package aurocosh.divinefavor.client.core.handler.ender_pumpkin

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.item.ItemBlockEnderPumpkin
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID, value = [Side.CLIENT])
object HUDHandler {
    private val ENDER_PUMPKIN_BLUR_TEX = ResourceLocation(ConstResources.GUI_ENDER_PUMPKIN)

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    fun onDraw(event: RenderGameOverlayEvent.Post) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL)
            return

        val player = DivineFavor.proxy.clientPlayer
        val stack = player.inventory.armorInventory[3]
        if (stack.item !is ItemBlockEnderPumpkin)
            return

        val mc = Minecraft.getMinecraft()
        val resolution = event.resolution

        GlStateManager.disableDepth()
        GlStateManager.depthMask(false)
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO)
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        GlStateManager.disableAlpha()
        mc.textureManager.bindTexture(ENDER_PUMPKIN_BLUR_TEX)
        val tessellator = Tessellator.getInstance()

        val height = resolution.scaledHeight.toDouble()
        val width = resolution.scaledWidth.toDouble()
        val bufferBuilder = tessellator.buffer
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX)
        bufferBuilder.pos(0.0, height, -90.0).tex(0.0, 1.0).endVertex()
        bufferBuilder.pos(width, height, -90.0).tex(1.0, 1.0).endVertex()
        bufferBuilder.pos(width, 0.0, -90.0).tex(1.0, 0.0).endVertex()
        bufferBuilder.pos(0.0, 0.0, -90.0).tex(0.0, 0.0).endVertex()

        tessellator.draw()
        GlStateManager.depthMask(true)
        GlStateManager.enableDepth()
        GlStateManager.enableAlpha()
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
    }
}
