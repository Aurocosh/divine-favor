package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import aurocosh.divinefavor.common.potions.common.ModPotions
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
import org.lwjgl.opengl.GL11

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionPrismaticEyes : ModPotionToggle("prismatic_eyes", true, 0x7FB8A4) {
    companion object {
        private val EVIL_PRISMATIC_EYES_TEXTURE = ResourceLocation(ConstResources.GUI_PRISMATIC_EYES_BLUR)

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        fun onDraw(event: RenderGameOverlayEvent.Post) {
            if (event.type != RenderGameOverlayEvent.ElementType.ALL)
                return

            val mc = Minecraft.getMinecraft()
            if (!mc.player.isPotionActive(ModPotions.prismatic_eyes))
                return

            val resolution = event.resolution

            GlStateManager.disableDepth()
            GlStateManager.enableBlend()
            GlStateManager.depthMask(false)
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
            GlStateManager.color(1.0f, 1.0f, 1.0f, 0.2f)
            mc.textureManager.bindTexture(EVIL_PRISMATIC_EYES_TEXTURE)
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
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        }
    }
}
