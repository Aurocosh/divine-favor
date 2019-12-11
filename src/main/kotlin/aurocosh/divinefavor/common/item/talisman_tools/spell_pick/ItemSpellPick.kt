package aurocosh.divinefavor.common.item.talisman_tools.spell_pick

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.entries.items.SpellPick
import aurocosh.divinefavor.common.constants.ConstGuiIDs
import aurocosh.divinefavor.common.item.base.ModItemPickaxe
import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContextGenerator
import aurocosh.divinefavor.common.item.talisman.IStackContainerProvider
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.item.talisman_tools.BookPropertyWrapper
import aurocosh.divinefavor.common.item.talisman_tools.IStackContainer
import aurocosh.divinefavor.common.item.talisman_tools.CastableAdapter
import aurocosh.divinefavor.common.item.talisman_tools.TalismanContainerMode
import aurocosh.divinefavor.common.item.talisman_tools.spell_pick.capability.SpellPickDataHandler.CAPABILITY_SPELL_PICK
import aurocosh.divinefavor.common.item.talisman_tools.spell_pick.capability.SpellPickProvider
import aurocosh.divinefavor.common.item.talisman_tools.spell_pick.capability.SpellPickStorage
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.lib.extensions.isNotEmpty
import aurocosh.divinefavor.common.lib.interfaces.IBlockCatcher
import aurocosh.divinefavor.common.stack_actions.StackAction
import aurocosh.divinefavor.common.stack_actions.StackActionHandler
import aurocosh.divinefavor.common.stack_actions.interfaces.IActionAccessor
import aurocosh.divinefavor.common.stack_actions.interfaces.IActionContainer
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.stack_properties.interfaces.IStackPropertyAccessor
import aurocosh.divinefavor.common.stack_properties.interfaces.IStackPropertyContainer
import aurocosh.divinefavor.common.util.UtilItemStack.actionResult
import aurocosh.divinefavor.common.util.UtilItemStack.actionResultPass
import com.google.common.collect.Multimap
import net.minecraft.block.state.IBlockState
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.SharedMonsterAttributes.ATTACK_DAMAGE
import net.minecraft.entity.SharedMonsterAttributes.ATTACK_SPEED
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.*
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.oredict.OreDictionary
import kotlin.math.max

open class ItemSpellPick(name: String, texturePath: String, orderIndex: Int = 0, val config: SpellPick, val material: ToolMaterial) : ModItemPickaxe(name, texturePath, orderIndex, material), IStackContainerProvider, IStackPropertyContainer, IActionContainer, IBlockCatcher {
    protected val propertyHandler: StackPropertyHandler = StackPropertyHandler(name)
    override val properties: IStackPropertyAccessor = propertyHandler
    protected val actionHandler: StackActionHandler = StackActionHandler(name)
    override val actions: IActionAccessor = actionHandler
    private val bookPropertyWrapper = BookPropertyWrapper(propertyHandler)

    override fun findProperty(stack: ItemStack, item: Item, propertyName: String) =
            CastableAdapter.findProperty(stack, item, propertyName, this, propertyHandler, CAPABILITY_SPELL_PICK)
    override fun findAction(stack: ItemStack, item: Item, actionName: String): Pair<ItemStack, StackAction>? =
            CastableAdapter.findAction(stack, item, actionName, this, actionHandler, CAPABILITY_SPELL_PICK)

    init {
        creativeTab = DivineFavor.TAB_MAIN
        this.maxDamage = config.maxUses

        addPropertyOverride(ResourceLocation("book_mode")) { stack, _, _ -> bookPropertyWrapper.getValueForModel(stack) }
    }

    override fun onBlockStartBreak(itemstack: ItemStack, pos: BlockPos, player: EntityPlayer): Boolean {
        val success = performBreakCast(itemstack, pos, player);
        return !success
    }

    private fun performBreakCast(stack: ItemStack, pos: BlockPos, player: EntityPlayer): Boolean {
        val (talismanStack, talisman) = CastableAdapter.getCastableStack<ItemToolTalisman>(stack) ?: return true
        val context = CastContextGenerator.pick(talismanStack, player, pos, stack)
        val cast = talisman.cast(context)
        return cast && talisman.shouldBreakBlock(context)
    }

    override fun hitEntity(stack: ItemStack, target: EntityLivingBase, attacker: EntityLivingBase): Boolean {
        stack.damageItem(1, attacker)
        if (attacker !is EntityPlayer)
            return true

        if (bookPropertyWrapper.getModeOrTransform(stack, attacker) != TalismanContainerMode.NORMAL)
            return true

        val (talismanStack, talisman) = CastableAdapter.getCastableStack<ItemBladeTalisman>(stack) ?: return true
        val context = CastContextGenerator.blade(talismanStack, target, attacker, stack)
        talisman.cast(context)
        return true
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val containerStack = player.getHeldItem(hand)
        val wrapper = CastableAdapter.getCastableStack<ItemTalisman>(containerStack)
        val (talismanStack, talisman) = wrapper ?: return EnumActionResult.PASS
        val context = CastContextGenerator.useCast(player, world, pos, hand, facing, talismanStack, containerStack)
        val success = talisman.cast(context)
        return actionResultPass(success)
    }

    override fun onItemRightClick(worldIn: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        val success = performRightClickAction(worldIn, player, hand, stack)
        return actionResult(success, stack)
    }

    private fun performRightClickAction(world: World, player: EntityPlayer, hand: EnumHand, stack: ItemStack): Boolean {
        val mode = bookPropertyWrapper.getModeOrTransform(stack, player)
        if (mode == TalismanContainerMode.INVALID)
            return false
        if (mode == TalismanContainerMode.BOOK)
            player.openGui(DivineFavor, ConstGuiIDs.SPELL_PICK, world, player.posX.toInt(), player.posY.toInt(), player.posZ.toInt())
        else if (mode == TalismanContainerMode.NORMAL) {
            val (talismanStack, talisman) = CastableAdapter.getCastableStack<ItemTalisman>(stack) ?: return true
            val context = CastContextGenerator.rightClick(world, player, hand, talismanStack, stack)
            talisman.cast(context)
        }
        return true
    }

    override fun getToolClasses(stack: ItemStack): Set<String> {
        val (talismanStack, talisman) = CastableAdapter.getCastableStack<ItemToolTalisman>(stack)
                ?: return super.getToolClasses(stack)
        if (talisman.isCustomToolClasses(talismanStack))
            return talisman.getCustomToolClasses(talismanStack)
        return super.getToolClasses(stack)
    }

    @SideOnly(Side.CLIENT)
    override fun isFull3D(): Boolean {
        return true
    }

    override fun getItemEnchantability(): Int {
        return config.enchantability
    }

    override fun getIsRepairable(toRepair: ItemStack, repair: ItemStack): Boolean {
        val stack = material.repairItemStack
        if (stack.isEmpty)
            return super.getIsRepairable(toRepair, repair)
        if (!OreDictionary.itemMatches(stack, repair, false)) return super.getIsRepairable(toRepair, repair)
        return true
    }

    override fun catchDrops(stack: ItemStack, toolStack: ItemStack, event: BlockEvent.HarvestDropsEvent) {
        val talismanStack = getSelectedStack(stack)
        if (talismanStack.isEmpty)
            return
        val catcher = talismanStack.item as IBlockCatcher
        catcher.catchDrops(talismanStack, stack, event)
    }

    override fun getHarvestLevel(stack: ItemStack, toolClass: String, player: EntityPlayer?, blockState: IBlockState?): Int {
        val pickHarvestLevel =  super.getHarvestLevel(stack, toolClass, player, blockState)
        val (talismanStack, talisman) = CastableAdapter.getCastableStack<ItemToolTalisman>(stack) ?: return pickHarvestLevel
        val talismanHarvestLevel = talisman.getHarvestLevel(talismanStack, toolClass, player, blockState)
        return max(pickHarvestLevel, talismanHarvestLevel)
    }

    override fun canHarvestBlock(state: IBlockState, stack: ItemStack): Boolean {
        val toolCanHarvest = super.canHarvestBlock(state, stack)
        val (talismanStack, talisman) = CastableAdapter.getCastableStack<ItemToolTalisman>(stack) ?: return toolCanHarvest
        return talisman.canHarvest(talismanStack, state, toolCanHarvest)
    }

    fun getMiningSpeed(stack: ItemStack, event: PlayerEvent.BreakSpeed) {
        val (talismanStack, talisman) = CastableAdapter.getCastableStack<ItemToolTalisman>(stack) ?: return
        val spiritData = event.entityPlayer.divinePlayerData.spiritData
        val favor = spiritData.getFavor(talisman.spiritId)
        if (favor < talisman.getApproximateFavorCost(stack))
            return
        talisman.getMiningSpeed(talismanStack, event)
    }

    override fun getItemAttributeModifiers(equipmentSlot: EntityEquipmentSlot): Multimap<String, AttributeModifier> {
        val multimap = super.getItemAttributeModifiers(equipmentSlot)

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(ATTACK_DAMAGE.name, AttributeModifier(ATTACK_DAMAGE_MODIFIER, ATTRIBUTE_NAME, config.damage.toDouble(), 0))
            multimap.put(ATTACK_SPEED.name, AttributeModifier(ATTACK_SPEED_MODIFIER, ATTRIBUTE_NAME, -config.attackSpeed.toDouble(), 0))
        }

        return multimap
    }

    override fun initCapabilities(item: ItemStack, nbt: NBTTagCompound?): ICapabilityProvider? {
        return if (item.item is ItemSpellPick) SpellPickProvider() else null
    }

    override fun getStackContainer(stack: ItemStack): IStackContainer = stack.cap(CAPABILITY_SPELL_PICK)

    override fun getSelectedStack(stack: ItemStack): ItemStack {
        if (stack.item !== this)
            return ItemStack.EMPTY
        return stack.cap(CAPABILITY_SPELL_PICK).getSelectedStack()
    }

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flag: ITooltipFlag) {
        super.addInformation(stack, world, tooltip, flag)

        val talismanTool = getStackContainer(stack)
        val talismanCount = talismanTool.getAllStacks().filter (ItemStack::isNotEmpty).count()
        val countMessage = I18n.format("tooltip.divinefavor:talisman_tool.talisman_count", talismanCount)
        tooltip.add(countMessage)

        properties.getPropertyTooltip(stack).forEach { tooltip.add(it) }
    }

    override fun getShareTag(): Boolean {
        return true
    }

    override fun getNBTShareTag(stack: ItemStack): NBTTagCompound? {
        var tag = super.getNBTShareTag(stack)
        if (tag == null)
            tag = NBTTagCompound()

        val grimoireHandler = stack.cap(CAPABILITY_SPELL_PICK)
        val tagShare = SpellPickStorage.getNbtBase(grimoireHandler)
        tag.setTag(TAG_SHARE, tagShare)
        return tag
    }

    override fun readNBTShareTag(stack: ItemStack, nbt: NBTTagCompound?) {
        super.readNBTShareTag(stack, nbt)
        if (nbt == null)
            return

        val grimoireHandler = stack.cap(CAPABILITY_SPELL_PICK)
        val tagShare = nbt.getCompoundTag(TAG_SHARE)
        SpellPickStorage.readNbtBase(grimoireHandler, tagShare)
    }

    companion object {
        const val SLOT_COUNT = 18
        const val ATTRIBUTE_NAME = "Weapon modifier"
        private const val TAG_SHARE = "SpellBlade"

        private val EffectiveOn = setOf(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE)
    }
}

