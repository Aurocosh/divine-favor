package aurocosh.divinefavor.common.item.gems

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.global.dayClock.DayClock
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilDayTime
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class ItemMarkedGlass(val spirit: ModSpirit, orderIndex: Int) : ModItem("marked_glass_" + spirit.name, "marked_glasses/" + spirit.name, orderIndex) {

    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_GEMS
    }

    override fun onItemRightClick(world: World?, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        val result = if (senseSpiritActivity(player)) EnumActionResult.SUCCESS else EnumActionResult.PASS
        return ActionResult(result, stack)
    }

    private fun senseSpiritActivity(player: EntityPlayer): Boolean {
        if (player.world.isRemote)
            return false

        if (spirit.isActive) {
            player.sendStatusMessage(TextComponentTranslation("message.divinefavor:marked_glass.spirit_is_active"), true)

        } else {
            val fromTime = DayClock.timeTicks
            val toTime = spirit.activityPeriod.start

            val ticksToActivity = UtilDayTime.ticksBetween(fromTime, toTime)
            val hours = ticksToActivity / UtilDayTime.TICKS_IN_HOUR
            val vartemp = ticksToActivity % UtilDayTime.TICKS_IN_HOUR
            val minutes = vartemp / UtilDayTime.TICKS_IN_MINUTE

            player.sendStatusMessage(TextComponentTranslation("message.divinefavor:marked_glass.spirit_is_inactive", hours, minutes), true)
        }
        return false
    }

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flag: ITooltipFlag) {
        super.addInformation(stack, world, tooltip, flag)

        val state = if(spirit.isActive) "active" else "inactive"
        val translationKey = "tooltip.divinefavor:marked_glass.spirit_is_$state"
        tooltip.add(I18n.format(translationKey))
    }
}