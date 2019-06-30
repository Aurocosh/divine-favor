package aurocosh.divinefavor.common.item.talisman_tools.spell_blade

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.entries.items.SpellBlade
import aurocosh.divinefavor.common.constants.ConstGuiIDs
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContextGenerator
import aurocosh.divinefavor.common.item.talisman.ITalismanStackContainer
import aurocosh.divinefavor.common.item.talisman.ITalismanToolContainer
import aurocosh.divinefavor.common.item.talisman_tools.BookPropertyWrapper
import aurocosh.divinefavor.common.item.talisman_tools.ITalismanTool
import aurocosh.divinefavor.common.item.talisman_tools.TalismanAdapter
import aurocosh.divinefavor.common.item.talisman_tools.TalismanContainerMode
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.capability.SpellBladeDataHandler.CAPABILITY_SPELL_BLADE
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.capability.SpellBladeProvider
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.capability.SpellBladeStorage
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.lib.interfaces.IBlockCatcher
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.stack_properties.interfaces.IPropertyAccessor
import aurocosh.divinefavor.common.stack_properties.interfaces.IPropertyContainer
import aurocosh.divinefavor.common.util.UtilItem.actionResult
import aurocosh.divinefavor.common.util.UtilItem.actionResultPass
import com.google.common.collect.Multimap
import net.minecraft.block.state.IBlockState
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
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.oredict.OreDictionary

open class ItemSpellBlade(name: String, texturePath: String, orderIndex: Int = 0, val config: SpellBlade, val material: ToolMaterial) : ModItem(name, texturePath, orderIndex), ITalismanStackContainer, ITalismanToolContainer, IPropertyContainer, IBlockCatcher {
    protected val propertyHandler: StackPropertyHandler = StackPropertyHandler(name)
    override val properties: IPropertyAccessor = propertyHandler
    private val bookPropertyWrapper = BookPropertyWrapper(propertyHandler)

    init {
        creativeTab = DivineFavor.TAB_MAIN
        this.maxDamage = config.maxUses

        addPropertyOverride(ResourceLocation("book_mode")) { stack, _, _ -> bookPropertyWrapper.getValueForModel(stack) }
    }

    override fun findProperty(stack: ItemStack, item: Item, propertyName: String) =
            TalismanAdapter.findProperty(stack, item, propertyName, this, propertyHandler, CAPABILITY_SPELL_BLADE)

    override fun hitEntity(stack: ItemStack, target: EntityLivingBase, attacker: EntityLivingBase): Boolean {
        stack.damageItem(1, attacker)
        if (attacker !is EntityPlayer)
            return true
        if (bookPropertyWrapper.getModeOrTransform(stack, attacker) != TalismanContainerMode.NORMAL)
            return true

        val (talismanStack, talisman) = TalismanAdapter.getTalisman<ItemBladeTalisman>(stack) ?: return true
        val context = TalismanContextGenerator.blade(talismanStack, target, attacker, stack)
        talisman.cast(context)
        return true
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val containerStack = player.getHeldItem(hand)
        if (bookPropertyWrapper.getModeOrTransform(containerStack, player) != TalismanContainerMode.NORMAL)
            return EnumActionResult.PASS

        val (talismanStack, talisman) = TalismanAdapter.getTalisman<ItemSpellTalisman>(containerStack)
                ?: return EnumActionResult.PASS
        val context = TalismanContextGenerator.useCast(player, world, pos, hand, facing, talismanStack, containerStack)
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
            player.openGui(DivineFavor, ConstGuiIDs.SPELL_BLADE, world, player.posX.toInt(), player.posY.toInt(), player.posZ.toInt())
        else if (mode == TalismanContainerMode.NORMAL) {
            val (talismanStack, talisman) = TalismanAdapter.getTalisman<ItemSpellTalisman>(stack) ?: return true
            val context = TalismanContextGenerator.rightClick(world, player, hand, talismanStack, stack)
            talisman.cast(context)
        }
        return true
    }

    override fun getDestroySpeed(stack: ItemStack, state: IBlockState): Float {
        return when {
            state.block === Blocks.WEB -> 15.0f
            else -> 1.5f
        }
    }

    override fun onBlockDestroyed(stack: ItemStack, worldIn: World, state: IBlockState, pos: BlockPos, entityLiving: EntityLivingBase): Boolean {
        if (state.getBlockHardness(worldIn, pos).toDouble() != 0.0) stack.damageItem(2, entityLiving)
        return true
    }

    override fun canHarvestBlock(blockIn: IBlockState): Boolean {
        return blockIn.block === Blocks.WEB
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

    override fun getItemAttributeModifiers(equipmentSlot: EntityEquipmentSlot): Multimap<String, AttributeModifier> {
        val multimap = super.getItemAttributeModifiers(equipmentSlot)

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(ATTACK_DAMAGE.name, AttributeModifier(ATTACK_DAMAGE_MODIFIER, ATTRIBUTE_NAME, config.damage.toDouble(), 0))
            multimap.put(ATTACK_SPEED.name, AttributeModifier(ATTACK_SPEED_MODIFIER, ATTRIBUTE_NAME, -config.attackSpeed.toDouble(), 0))
        }

        return multimap
    }

    override fun initCapabilities(item: ItemStack, nbt: NBTTagCompound?): ICapabilityProvider? {
        return if (item.item is ItemSpellBlade) SpellBladeProvider() else null
    }

    override fun getTalismanTool(stack: ItemStack): ITalismanTool = stack.cap(CAPABILITY_SPELL_BLADE)

    override fun catchDrops(stack: ItemStack, toolStack: ItemStack, event: BlockEvent.HarvestDropsEvent) {
        val talismanStack = getTalismanStack(stack)
        if (talismanStack.isEmpty)
            return
        val catcher = talismanStack.item as IBlockCatcher
        catcher.catchDrops(talismanStack, toolStack, event)
    }

    override fun getTalismanStack(stack: ItemStack): ItemStack {
        if (stack.item !== this)
            return ItemStack.EMPTY
        return stack.cap(CAPABILITY_SPELL_BLADE).getSelectedStack()
    }

    override fun getShareTag(): Boolean {
        return true
    }

    override fun getNBTShareTag(stack: ItemStack): NBTTagCompound? {
        var tag = super.getNBTShareTag(stack)
        if (tag == null)
            tag = NBTTagCompound()

        val grimoireHandler = stack.cap(CAPABILITY_SPELL_BLADE)
        val tagShare = SpellBladeStorage.getNbtBase(grimoireHandler)
        tag.setTag(TAG_SHARE, tagShare)
        return tag
    }

    override fun readNBTShareTag(stack: ItemStack, nbt: NBTTagCompound?) {
        super.readNBTShareTag(stack, nbt)
        if (nbt == null)
            return

        val grimoireHandler = stack.cap(CAPABILITY_SPELL_BLADE)
        val tagShare = nbt.getCompoundTag(TAG_SHARE)
        SpellBladeStorage.readNbtBase(grimoireHandler, tagShare)
    }

    companion object {
        const val SLOT_COUNT = 18
        const val ATTRIBUTE_NAME = "Weapon modifier"
        private const val TAG_SHARE = "SpellBlade"
    }
}

