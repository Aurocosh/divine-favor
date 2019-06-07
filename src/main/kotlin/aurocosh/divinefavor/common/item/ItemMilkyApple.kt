package aurocosh.divinefavor.common.item

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Items
import net.minecraft.item.EnumAction
import net.minecraft.item.ItemStack
import net.minecraft.stats.StatList
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World

class ItemMilkyApple : ModItem("milky_apple", "milky_apple", ConstMainTabOrder.OTHER_ITEMS) {
    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun onItemUseFinish(stack: ItemStack, worldIn: World, entityLiving: EntityLivingBase): ItemStack {
        if (!worldIn.isRemote)
            entityLiving.curePotionEffects(stack) // FORGE - move up so stack.shrink does not turn stack into air
        if (entityLiving is EntityPlayerMP) {
            val statBase = StatList.getObjectUseStats(this) ?: return stack
            entityLiving.addStat(statBase)
            CriteriaTriggers.CONSUME_ITEM.trigger(entityLiving, stack)
        }

        if (entityLiving is EntityPlayer && !entityLiving.capabilities.isCreativeMode)
            stack.shrink(1)

        return stack
    }

    override fun getMaxItemUseDuration(stack: ItemStack?): Int {
        return 32
    }

    override fun getItemUseAction(stack: ItemStack?): EnumAction {
        return EnumAction.EAT
    }

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        playerIn.activeHand = handIn
        return ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn))
    }
}