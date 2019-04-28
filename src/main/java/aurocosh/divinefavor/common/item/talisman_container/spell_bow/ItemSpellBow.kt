package aurocosh.divinefavor.common.item.talisman_container.spell_bow

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGuiIDs
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.capability.SpellBowDataHandler
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.capability.SpellBowProvider
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.capability.SpellBowStorage
import aurocosh.divinefavor.common.util.UtilBow
import aurocosh.divinefavor.common.util.UtilNbt
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.enchantment.EnchantmentHelper
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

class ItemSpellBow : ModItem("spell_bow", "spell_bow/spell_bow", ConstMainTabOrder.CONTAINERS) {
    init {
        maxStackSize = 1
        creativeTab = DivineFavor.TAB_MAIN
        maxDamage = 384

        addPropertyOverride(ResourceLocation("book_mode")) { stack, world, entityLivingBase ->
            val nbt = UtilNbt.getNbt(stack)
            (if (nbt.getBoolean(TAG_IS_IN_BOOK_MODE)) 1 else 0).toFloat()
        }
        addPropertyOverride(ResourceLocation("pull")) { stack, world, entityLivingBase ->
            if (entityLivingBase == null)
                0.0f
            else
                if (entityLivingBase.activeItemStack.item !== ModItems.spell_bow) 0.0f else (stack.maxItemUseDuration - entityLivingBase.itemInUseCount).toFloat() / 20.0f
        }
        addPropertyOverride(ResourceLocation("pulling")) { stack, world, entity -> if (entity != null && entity.isHandActive && entity.activeItemStack == stack) 1.0f else 0.0f }
    }

    private fun findAmmo(player: EntityPlayer): ItemStack {
        return UtilPlayer.findStackInInventory(player) { stack -> stack.item is ItemArrow }.stack
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    override fun onPlayerStoppedUsing(bowStack: ItemStack?, world: World?, entityLiving: EntityLivingBase?, timeLeft: Int) {
        if (UtilNbt.getNbt(bowStack!!).getBoolean(TAG_IS_IN_BOOK_MODE))
            return
        if (entityLiving !is EntityPlayer)
            return

        val player = entityLiving as EntityPlayer?
        val unlimitedArrows = player!!.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bowStack) > 0
        var arrowStack = findAmmo(player)

        var charge = getMaxItemUseDuration(bowStack) - timeLeft
        charge = ForgeEventFactory.onArrowLoose(bowStack, world, player, charge, !arrowStack.isEmpty || unlimitedArrows)
        if (charge < 0)
            return
        if (arrowStack.isEmpty && !unlimitedArrows)
            return

        if (arrowStack.isEmpty)
            arrowStack = ItemStack(Items.ARROW)

        val velocity = UtilBow.getArrowVelocity(charge)
        if (velocity.toDouble() < 0.1)
            return

        val stackIsInfinite = player.capabilities.isCreativeMode || arrowStack.item is ItemArrow && (arrowStack.item as ItemArrow).isInfinite(arrowStack, bowStack, player)
        if (!world!!.isRemote) {
            val talisman = SpellBowDataHandler.get(bowStack)!!.getSelectedTalisman()
            val entityArrow: EntityArrow
            if (talisman != null && talisman.claimCost(world, player))
                entityArrow = talisman.createArrow(world, talisman, player)
            else
                entityArrow = getStandardArrow(world, arrowStack, player)
            entityArrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0f, velocity * 3.0f, 1.0f)

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

            bowStack.damageItem(1, player)

            if (stackIsInfinite || player.capabilities.isCreativeMode && (arrowStack.item === Items.SPECTRAL_ARROW || arrowStack.item === Items.TIPPED_ARROW))
                entityArrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY
            world.spawnEntity(entityArrow)
            talisman?.postInit(entityArrow)
        }

        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f / (Item.itemRand.nextFloat() * 0.4f + 1.2f) + velocity * 0.5f)

        if (!stackIsInfinite && !player.capabilities.isCreativeMode)
            UtilPlayer.damageStack(player, arrowStack)
        player.addStat(StatList.getObjectUseStats(this)!!)
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
    override fun onItemRightClick(worldIn: World?, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        val itemstack = playerIn.getHeldItem(handIn)
        if (itemstack.item !is ItemSpellBow)
            return ActionResult(EnumActionResult.PASS, itemstack)

        val nbt = UtilNbt.getNbt(itemstack)
        val isBook = nbt.getBoolean(TAG_IS_IN_BOOK_MODE)
        if (playerIn.isSneaking) {
            nbt.setBoolean(TAG_IS_IN_BOOK_MODE, !isBook)
            return ActionResult(EnumActionResult.SUCCESS, itemstack)
        }
        return if (isBook)
            doBookAction(worldIn, playerIn, itemstack)
        else
            doBowAction(worldIn, playerIn, handIn, itemstack)
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
        return 1
    }

    override fun initCapabilities(item: ItemStack, nbt: NBTTagCompound?): ICapabilityProvider? {
        return if (item.item === ModItems.spell_bow) SpellBowProvider() else null
    }

    override fun getShareTag(): Boolean {
        return true
    }

    override fun getNBTShareTag(stack: ItemStack): NBTTagCompound? {
        var tag = super.getNBTShareTag(stack)
        if (tag == null)
            tag = NBTTagCompound()

        val bowHandler = SpellBowDataHandler.get(stack);
        val tagShare = SpellBowStorage.getNbtBase(bowHandler!!)
        tag.setTag(TAG_SHARE, tagShare)
        return tag
    }

    override fun readNBTShareTag(stack: ItemStack, nbt: NBTTagCompound?) {
        super.readNBTShareTag(stack, nbt)
        if (nbt == null)
            return
        val bowHandler = SpellBowDataHandler.get(stack);
        val tagShare = nbt.getCompoundTag(TAG_SHARE)
        SpellBowStorage.readNbtBase(bowHandler!!, tagShare)
    }

    companion object {
        val SIZE = 27
        val TAG_IS_IN_BOOK_MODE = "IsInBookMode"
        private val TAG_SHARE = "SpellBowShare"
    }
}