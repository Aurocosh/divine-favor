package aurocosh.divinefavor.client.core.handler.stable_gem

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.gems.base.IUsableGemItem
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType
import net.minecraftforge.fml.common.Mod
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
        val hand = UtilPlayer.getHandWithItem(player) { it is IUsableGemItem } ?: return
        val stack = player.getHeldItem(hand)

        val resolution = event.resolution
        val width = resolution.scaledWidth
        val height = resolution.scaledHeight
        val mc = Minecraft.getMinecraft()
        WarpMarkerHUD.drawGemDescription(mc, width, height, player, stack, false)
    }
}