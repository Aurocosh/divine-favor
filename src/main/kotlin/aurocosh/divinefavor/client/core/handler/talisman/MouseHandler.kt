package aurocosh.divinefavor.client.core.handler.talisman

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.client.core.handler.KeyBindings
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.item.talisman_tools.TalismanContainerAdapter
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
        if (!KeyBindings.talismanValueHud.isKeyDown)
            return

        val dWheel = event.dwheel
        if (dWheel == 0)
            return

        val player = DivineFavor.proxy.clientPlayer
        val hand = UtilPlayer.getHandWithItem(player) { it is ItemTalisman } ?: return
        val stack = player.getHeldItem(hand)


//
//        if (dWheel < 0)
//            talismanContainer.switchToPrevious()
//        else
//            talismanContainer.switchToNext()
//        val playerSlot = if (hand == EnumHand.OFF_HAND) 40 else player.inventory.currentItem
//        TalismanContainerAdapter.selectSlot(playerSlot, talismanContainer.selectedSlotIndex)
//        event.isCanceled = true
    }
}