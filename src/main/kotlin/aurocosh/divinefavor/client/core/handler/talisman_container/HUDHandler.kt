package aurocosh.divinefavor.client.core.handler.talisman_container

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.client.core.handler.KeyBindings
import aurocosh.divinefavor.client.core.handler.talisman.TalismanHUD
import aurocosh.divinefavor.common.item.talisman_container.TalismanContainerAdapter
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID, value = [Side.CLIENT])
object HUDHandler {
    private val scrollHUD = TalismanScrollHUD()

    @SubscribeEvent
    fun onDraw(event: RenderGameOverlayEvent.Post) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL)
            return

        val resolution = event.resolution
        val width = resolution.scaledWidth
        val height = resolution.scaledHeight

        val player = DivineFavor.proxy.clientPlayer
        val stack = UtilPlayer.getItemInHand(player) { TalismanContainerAdapter.isItemValid(it) }
        if (stack.isEmpty)
            return

        val talismanContainer = TalismanContainerAdapter.getTalismanContainer(stack) ?: return

        val mc = Minecraft.getMinecraft()

        if (KeyBindings.talismanScroll.isKeyDown)
            scrollHUD.draw(mc, width, height, talismanContainer)

        if (mc.currentScreen !== TalismanSelectGui.INSTANCE)
            TalismanHUD.drawTalismanDescription(mc, width, height, player, talismanContainer.getSelectedStack(), true)
    }
}
