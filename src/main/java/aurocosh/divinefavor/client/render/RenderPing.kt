package aurocosh.divinefavor.client.render

import aurocosh.divinefavor.client.models.ModelPing
import aurocosh.divinefavor.common.entity.EntityPing
import aurocosh.divinefavor.common.util.UtilLighting
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.GlStateManager.DestFactor
import net.minecraft.client.renderer.GlStateManager.SourceFactor
import net.minecraft.client.renderer.entity.Render
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.ResourceLocation

class RenderPing(renderManager: RenderManager) : Render<EntityPing>(renderManager) {

    override fun doRender(ping: EntityPing, x: Double, y: Double, z: Double, yaw: Float, partialTicks: Float) {
        bindEntityTexture(ping)

        GlStateManager.pushMatrix()

        GlStateManager.enableBlend()
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA)
        GlStateManager.enableTexture2D()
        GlStateManager.enableLighting()

        GlStateManager.color(1f, 1f, 1f, 1f)
        UtilLighting.INSTANCE.setLighting(255)

        GlStateManager.pushMatrix()
        GlStateManager.translate(x, y, z)
        drawModel(ping, partialTicks)
        GlStateManager.popMatrix()

        UtilLighting.INSTANCE.revert()
        GlStateManager.enableTexture2D()
        GlStateManager.enableLighting()

        GlStateManager.popMatrix()
    }

    private fun drawModel(entityPing: EntityPing, partialTicks: Float) {
        MODEL_PING.render(entityPing, 0f, 0f, 0f, 0f, 0f, 0.0625f)
    }

    override fun getEntityTexture(ping: EntityPing): ResourceLocation? {
        return TEXTURE
    }

    companion object {
        private val TEXTURE = ResourceLocation("divinefavor:textures/blocks/rope_nodes.png")
        private val MODEL_PING = ModelPing()
    }
}