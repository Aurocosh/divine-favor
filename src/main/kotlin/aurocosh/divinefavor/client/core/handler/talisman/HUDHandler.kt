package aurocosh.divinefavor.client.core.handler.talisman

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID, value = [Side.CLIENT])
object HUDHandler {
    @SubscribeEvent
    fun onDraw(event: RenderGameOverlayEvent.Post) {
        if (event.type != ElementType.ALL)
            return

        val player = DivineFavor.proxy.clientPlayer
        val hand = UtilPlayer.getHandWithItem(player) { it is ItemTalisman } ?: return
        val stack = player.getHeldItem(hand)

        val resolution = event.resolution
        val width = resolution.scaledWidth
        val height = resolution.scaledHeight
        val mc = Minecraft.getMinecraft()
        TalismanHUD.drawTalismanDescription(mc, width, height, player, stack, false)
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun renderWorldLastEvent(lastEvent: RenderWorldLastEvent) {
        val player = DivineFavor.proxy.clientPlayer
        val hand = UtilPlayer.getHandWithItem(player) { it is ItemTalisman } ?: return
        val stack = player.getHeldItem(hand)
        val talisman = stack.item as ItemTalisman

        val context = TalismanContext.generic(player, hand, stack)
        talisman.handleRendering(context, lastEvent)
    }
}