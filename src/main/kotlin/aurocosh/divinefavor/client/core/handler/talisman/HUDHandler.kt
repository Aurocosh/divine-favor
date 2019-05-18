package aurocosh.divinefavor.client.core.handler.talisman

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman
import net.minecraft.client.Minecraft
import net.minecraft.util.EnumHand
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID, value = [Side.CLIENT])
object HUDHandler {
    @SubscribeEvent
    fun onDraw(event: RenderGameOverlayEvent.Post) {
        if (event.type != ElementType.ALL)
            return

        val player = DivineFavor.proxy.clientPlayer
        val stack = player.getHeldItem(EnumHand.MAIN_HAND)
        if (stack.item !is ItemTalisman)
            return

        val resolution = event.resolution
        val width = resolution.scaledWidth
        val height = resolution.scaledHeight
        val mc = Minecraft.getMinecraft()
        TalismanHUD.drawTalismanDescription(mc, width, height, player, stack, false)
    }
}