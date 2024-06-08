package aurocosh.divinefavor.client.core.handler.common

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.client.core.handler.KeyBindings
import aurocosh.divinefavor.client.core.handler.talisman_container.TalismanScrollHUD
import aurocosh.divinefavor.client.core.handler.talisman_container.TalismanSelectGui
import aurocosh.divinefavor.common.item.talisman.ISelectedStackProvider
import aurocosh.divinefavor.common.item.talisman.IStackContainerProvider
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
    private val mc = Minecraft.getMinecraft()
    private val scrollHUD = TalismanScrollHUD()

    @SubscribeEvent
    @JvmStatic
    fun onDraw(event: RenderGameOverlayEvent.Post) {
        if (event.type != ElementType.ALL)
            return

        val player = DivineFavor.proxy.clientPlayer
        val hand = UtilPlayer.getHandWithItem(player) { it is ISelectedStackProvider } ?: return
        val stack = player.getHeldItem(hand)

        val resolution = event.resolution
        val width = resolution.scaledWidth
        val height = resolution.scaledHeight

        val item = stack.item
        if (KeyBindings.talismanScroll.isKeyDown && item is IStackContainerProvider) {
            val container = item.getStackContainer(stack)
            scrollHUD.draw(mc, width, height, container)
        }

        if (mc.currentScreen !== TalismanSelectGui.INSTANCE && item is ISelectedStackProvider) {
            val selectedStack = item.getSelectedStack(stack)
            val renderer = HudRenderers.getRenderer(selectedStack.item)
            renderer?.drawDescription(mc, width, height, player, selectedStack, stack != selectedStack)
        }
    }

}