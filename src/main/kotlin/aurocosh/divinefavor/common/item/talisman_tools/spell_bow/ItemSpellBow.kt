package aurocosh.divinefavor.common.item.talisman_tools.spell_bow

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigItem
import aurocosh.divinefavor.common.constants.ConstGuiIDs
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.arrow_talismans.base.ItemArrowTalisman
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContextGenerator
import aurocosh.divinefavor.common.item.talisman.ISelectedStackProvider
import aurocosh.divinefavor.common.item.talisman.IStackContainerProvider
import aurocosh.divinefavor.common.item.talisman_tools.BookPropertyWrapper
import aurocosh.divinefavor.common.item.talisman_tools.IStackContainer
import aurocosh.divinefavor.common.item.talisman_tools.CastableAdapter
import aurocosh.divinefavor.common.item.talisman_tools.TalismanContainerMode
import aurocosh.divinefavor.common.item.talisman_tools.spell_bow.capability.SpellBowDataHandler.CAPABILITY_SPELL_BOW
import aurocosh.divinefavor.common.item.talisman_tools.spell_bow.capability.SpellBowProvider
import aurocosh.divinefavor.common.item.talisman_tools.spell_bow.capability.SpellBowStorage
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.lib.extensions.isNotEmpty
import aurocosh.divinefavor.common.stack_actions.StackAction
import aurocosh.divinefavor.common.stack_actions.StackActionHandler
import aurocosh.divinefavor.common.stack_actions.interfaces.IActionAccessor
import aurocosh.divinefavor.common.stack_actions.interfaces.IActionContainer
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.stack_properties.interfaces.IStackPropertyAccessor
import aurocosh.divinefavor.common.stack_properties.interfaces.IStackPropertyContainer
import aurocosh.divinefavor.common.util.UtilBow
import aurocosh.divinefavor.common.util.UtilItemStack.actionResult
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.EnumEnchantmentType
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.entity.projectile.EntityTippedArrow
import net.minecraft.init.Enchantments
import net.minecraft.init.Items
import net.minecraft.init.SoundEvents
import net.minecraft.item.EnumAction
import net.minecraft.item.Item
import net.minecraft.item.ItemArrow
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.stats.StatList
import net.minecraft.util.*
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.event.ForgeEventFactory
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.oredict.OreDictionary

class ItemSpellBow : ModItem("spell_bow", "spell_bow/spell_bow", ConstMainTabOrder.CONTAINERS), ISelectedStackProvider, IStackContainerProvider, IStackPropertyContainer, IActionContainer {
    protected val propertyHandler: StackPropertyHandler = StackPropertyHandler("spell_bow")
    override val properties: IStackPropertyAccessor = propertyHandler
    protected val actionHandler: StackActionHandler = StackActionHandler("spell_bow")
    override val actions: IActionAccessor = actionHandler
    private val bookPropertyWrapper = BookPropertyWrapper(propertyHandler)
    private val material: ToolMaterial = ToolMaterial.WOOD

    override fun findProperty(stack: ItemStack, item: Item, propertyName: String) =
            CastableAdapter.findProperty(stack, item, propertyName, this, propertyHandler, CAPABILITY_SPELL_BOW)
    override fun findAction(stack: ItemStack, item: Item, actionName: String): Pair<ItemStack, StackAction>? =
            CastableAdapter.findAction(stack, item, actionName, this, actionHandler, CAPABILITY_SPELL_BOW)

    init {
        maxStackSize = 1
        creativeTab = DivineFavor.TAB_MAIN
        maxDamage = ConfigItem.spellBow.maxUses

        addPropertyOverride(ResourceLocation("book_mode")) { stack, _, _ -> bookPropertyWrapper.getValueForModel(stack) }
        addPropertyOverride(ResourceLocation("pulling")) { stack, _, entity -> if (entity != null && entity.isHandActive && entity.activeItemStack == stack) 1.0f else 0.0f }
        addPropertyOverride(ResourceLocation("pull")) { stack, _, entityLivingBase ->
            when {
                entityLivingBase == null -> 0.0f
                entityLivingBase.activeItemStack.item !== ModItems.spell_bow -> 0.0f
                else -> (stack.maxItemUseDuration - entityLivingBase.itemInUseCount).toFloat() / 20.0f
            }
        }
    }

    private fun findAmmo(player: EntityPlayer): ItemStack {
        return UtilPlayer.findStackInInventory(player) { stack -> stack.item is ItemArrow }.stack
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    override fun onPlayerStoppedUsing(bowStack: ItemStack, world: World, entityLiving: EntityLivingBase, timeLeft: Int) {
        if (entityLiving !is EntityPlayer)
            return
        val mode = bookPropertyWrapper.getModeOrTransform(bowStack, entityLiving)
        if (mode != TalismanContainerMode.NORMAL)
            return

        val unlimitedArrows = entityLiving.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bowStack) > 0
        var arrowStack = findAmmo(entityLiving)

        var charge = getMaxItemUseDuration(bowStack) - timeLeft
        charge = ForgeEventFactory.onArrowLoose(bowStack, world, entityLiving, charge, !arrowStack.isEmpty || unlimitedArrows)
        if (charge < 0)
            return
        if (arrowStack.isEmpty && !unlimitedArrows)
            return

        if (arrowStack.isEmpty)
            arrowStack = ItemStack(Items.ARROW)

        val velocity = UtilBow.getArrowVelocity(charge)
        if (velocity.toDouble() < 0.1)
            return

        val stackIsInfinite = entityLiving.capabilities.isCreativeMode || arrowStack.item is ItemArrow && (arrowStack.item as ItemArrow).isInfinite(arrowStack, bowStack, entityLiving)
        if (!world.isRemote) {

            val container = bowStack.item as ISelectedStackProvider
            val talismanStack = container.getSelectedStack(bowStack)

            val context = CastContextGenerator.arrowShot(world, entityLiving, talismanStack, bowStack)

            val (arrowTalisman, entityArrow) = getArrow(context, arrowStack)
            entityArrow.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0f, velocity * 3.0f, 1.0f)

            if (velocity == 1.0f)
                entityArrow.isCritical = true

            val powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, bowStack)
            if (powerLevel > 0)
                entityArrow.damage = entityArrow.damage + powerLevel.toDouble() * 0.5 + 0.5

            val punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, bowStack)
            if (punchLevel > 0)
                entityArrow.setKnockbackStrength(punchLevel)

            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, bowStack) > 0)
                entityArrow.setFire(100)

            bowStack.damageItem(1, entityLiving)

            if (stackIsInfinite || entityLiving.capabilities.isCreativeMode && (arrowStack.item === Items.SPECTRAL_ARROW || arrowStack.item === Items.TIPPED_ARROW))
                entityArrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY
            world.spawnEntity(entityArrow)
            arrowTalisman?.postInit(entityArrow)
        }

        world.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f / (Item.itemRand.nextFloat() * 0.4f + 1.2f) + velocity * 0.5f)

        if (!stackIsInfinite && !entityLiving.capabilities.isCreativeMode)
            UtilPlayer.damageStack(entityLiving, arrowStack)
        entityLiving.addStat(StatList.getObjectUseStats(this)!!)
    }

    private fun getArrow(context: CastContext, arrowStack: ItemStack): Pair<ItemArrowTalisman?, EntityArrow> {
        val talisman = context.stack.item
        if (talisman !is ItemArrowTalisman || !talisman.claimCost(context))
            return Pair(null, getStandardArrow(context.world, arrowStack, context.player))
        val arrow = talisman.createArrow(context)
        return Pair(talisman, arrow)
    }

    private fun getStandardArrow(world: World, arrowStack: ItemStack, shooter: EntityLivingBase): EntityArrow {
        val arrow = EntityTippedArrow(world, shooter)
        arrow.setPotionEffect(arrowStack)
        return arrow
    }

    /**
     * How long it takes to use or consume an item
     */
    override fun getMaxItemUseDuration(stack: ItemStack?): Int {
        return 72000
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    override fun getItemUseAction(stack: ItemStack?): EnumAction {
        return EnumAction.BOW
    }

    /**
     * Called when the equipped item is right clicked.
     */
    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val itemStack = player.getHeldItem(hand)
        if (itemStack.item !is ItemSpellBow)
            return ActionResult(EnumActionResult.PASS, itemStack)
        return when (bookPropertyWrapper.getModeOrTransform(itemStack, player)) {
            TalismanContainerMode.BOOK -> doBookAction(world, player, itemStack)
            TalismanContainerMode.NORMAL -> doBowAction(world, player, hand, itemStack)
            else -> return actionResult(false, itemStack)
        }
    }

    private fun doBookAction(world: World?, player: EntityPlayer, stack: ItemStack): ActionResult<ItemStack> {
        player.openGui(DivineFavor, ConstGuiIDs.SPELL_BOW, world!!, player.posX.toInt(), player.posY.toInt(), player.posZ.toInt())
        return ActionResult(EnumActionResult.SUCCESS, stack)
    }

    private fun doBowAction(worldIn: World?, playerIn: EntityPlayer, handIn: EnumHand, itemstack: ItemStack): ActionResult<ItemStack> {
        val gotArrows = !findAmmo(playerIn).isEmpty

        val ret = ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, gotArrows)
        if (ret != null)
            return ret

        if (!playerIn.capabilities.isCreativeMode && !gotArrows)
            return if (gotArrows) ActionResult(EnumActionResult.PASS, itemstack) else ActionResult(EnumActionResult.FAIL, itemstack)
        else {
            playerIn.activeHand = handIn
            return ActionResult(EnumActionResult.SUCCESS, itemstack)
        }
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    override fun getItemEnchantability(): Int {
        return ConfigItem.spellBow.enchantability
    }

    override fun canApplyAtEnchantingTable(stack: ItemStack, enchantment: net.minecraft.enchantment.Enchantment): Boolean {
        return if(enchantment.type == EnumEnchantmentType.BOW)
            true;
        else
            super.canApplyAtEnchantingTable(stack, enchantment)
    }

    override fun initCapabilities(item: ItemStack, nbt: NBTTagCompound?): ICapabilityProvider? {
        return if (item.item === ModItems.spell_bow) SpellBowProvider() else null
    }

    override fun getIsRepairable(toRepair: ItemStack, repair: ItemStack): Boolean {
        val stack = material.repairItemStack
        if (stack.isEmpty)
            return super.getIsRepairable(toRepair, repair)
        if (!OreDictionary.itemMatches(stack, repair, false)) return super.getIsRepairable(toRepair, repair)
        return true
    }

    override fun getStackContainer(stack: ItemStack): IStackContainer = stack.cap(CAPABILITY_SPELL_BOW)

    override fun getSelectedStack(stack: ItemStack): ItemStack {
        if (stack.item !== this)
            return ItemStack.EMPTY
        return stack.cap(CAPABILITY_SPELL_BOW).getSelectedStack()
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

        val bowHandler = stack.cap(CAPABILITY_SPELL_BOW)
        val tagShare = SpellBowStorage.getNbtBase(bowHandler)
        tag.setTag(TAG_SHARE, tagShare)
        return tag
    }

    override fun readNBTShareTag(stack: ItemStack, nbt: NBTTagCompound?) {
        super.readNBTShareTag(stack, nbt)
        if (nbt == null)
            return
        val bowHandler = stack.cap(CAPABILITY_SPELL_BOW)
        val tagShare = nbt.getCompoundTag(TAG_SHARE)
        SpellBowStorage.readNbtBase(bowHandler, tagShare)
    }

    companion object {
        const val SLOT_COUNT = 27
        private const val TAG_SHARE = "SpellBowShare"
    }
}