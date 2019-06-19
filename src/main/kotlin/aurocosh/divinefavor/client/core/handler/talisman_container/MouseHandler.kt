package aurocosh.divinefavor.client.core.handler.talisman_container

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.client.core.handler.KeyBindings
import aurocosh.divinefavor.common.item.talisman.ITalismanToolContainer
import aurocosh.divinefavor.common.item.talisman_tools.TalismanAdapter
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.util.EnumHand
import net.minecraftforge.client.event.MouseEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID, value = [Side.CLIENT])
object MouseHandler {
    @SubscribeEvent
    fun onMouseEvent(event: MouseEvent) {
        if (!KeyBindings.talismanScroll.isKeyDown)
            return

        val dWheel = event.dwheel
        if (dWheel == 0)
            return

        val player = DivineFavor.proxy.clientPlayer
        val hand = UtilPlayer.getHandWithItem(player) { it is ITalismanToolContainer } ?: return

        val stack = player.getHeldItem(hand)
        val toolContainer = stack.item as ITalismanToolContainer
        val talismanTool = toolContainer.getTalismanTool(stack)

        if (dWheel < 0)
            talismanTool.switchToPrevious()
        else
            talismanTool.switchToNext()
        val playerSlot = if (hand == EnumHand.OFF_HAND) 40 else player.inventory.currentItem
        TalismanAdapter.selectSlot(playerSlot, talismanTool.selectedSlotIndex)
        event.isCanceled = true
    }
}