package aurocosh.divinefavor.client.render.projectile

import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.entity.projectile.EntityIceArrow
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.entity.RenderArrow
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.MathHelper
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
class RenderIceArrow(manager: RenderManager) : RenderArrow<EntityIceArrow>(manager) {
    override fun getEntityTexture(entity: EntityIceArrow): ResourceLocation? {
        return RES_ICE_ARROW
    }

    override fun doRender(entity: EntityIceArrow, x: Double, y: Double, z: Double, entityYaw: Float, partialTicks: Float) {
        this.bindEntityTexture(entity)
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.3f)
        GlStateManager.pushMatrix()
        GlStateManager.disableLighting()
        GlStateManager.enableBlend()
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA)
        GlStateManager.translate(x.toFloat(), y.toFloat(), z.toFloat())
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0f, 0.0f, 1.0f, 0.0f)
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0f, 0.0f, 1.0f)
        val tessellator = Tessellator.getInstance()
        val bufferbuilder = tessellator.buffer
        val i = 0
        val f = 0.0f
        val f1 = 0.5f
        val f2 = 0.0f
        val f3 = 0.15625f
        val f4 = 0.0f
        val f5 = 0.15625f
        val f6 = 0.15625f
        val f7 = 0.3125f
        val f8 = 0.05625f
        GlStateManager.enableRescaleNormal()
        val f9 = entity.arrowShake.toFloat() - partialTicks

        if (f9 > 0.0f) {
            val f10 = -MathHelper.sin(f9 * 3.0f) * f9
            GlStateManager.rotate(f10, 0.0f, 0.0f, 1.0f)
        }

        GlStateManager.rotate(45.0f, 1.0f, 0.0f, 0.0f)
        GlStateManager.scale(0.05625f, 0.05625f, 0.05625f)
        GlStateManager.translate(-4.0f, 0.0f, 0.0f)

        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial()
            GlStateManager.enableOutlineMode(this.getTeamColor(entity))
        }

        GlStateManager.glNormal3f(0.05625f, 0.0f, 0.0f)
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX)
        bufferbuilder.pos(-7.0, -2.0, -2.0).tex(0.0, 0.15625).endVertex()
        bufferbuilder.pos(-7.0, -2.0, 2.0).tex(0.15625, 0.15625).endVertex()
        bufferbuilder.pos(-7.0, 2.0, 2.0).tex(0.15625, 0.3125).endVertex()
        bufferbuilder.pos(-7.0, 2.0, -2.0).tex(0.0, 0.3125).endVertex()
        tessellator.draw()
        GlStateManager.glNormal3f(-0.05625f, 0.0f, 0.0f)
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX)
        bufferbuilder.pos(-7.0, 2.0, -2.0).tex(0.0, 0.15625).endVertex()
        bufferbuilder.pos(-7.0, 2.0, 2.0).tex(0.15625, 0.15625).endVertex()
        bufferbuilder.pos(-7.0, -2.0, 2.0).tex(0.15625, 0.3125).endVertex()
        bufferbuilder.pos(-7.0, -2.0, -2.0).tex(0.0, 0.3125).endVertex()
        tessellator.draw()

        for (j in 0..3) {
            GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f)
            GlStateManager.glNormal3f(0.0f, 0.0f, 0.05625f)
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX)
            bufferbuilder.pos(-8.0, -2.0, 0.0).tex(0.0, 0.0).endVertex()
            bufferbuilder.pos(8.0, -2.0, 0.0).tex(0.5, 0.0).endVertex()
            bufferbuilder.pos(8.0, 2.0, 0.0).tex(0.5, 0.15625).endVertex()
            bufferbuilder.pos(-8.0, 2.0, 0.0).tex(0.0, 0.15625).endVertex()
            tessellator.draw()
        }

        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode()
            GlStateManager.disableColorMaterial()
        }

        GlStateManager.disableRescaleNormal()
        GlStateManager.enableLighting()
        GlStateManager.popMatrix()
        super.doRender(entity, x, y, z, entityYaw, partialTicks)

    }

    companion object {
        val RES_ICE_ARROW = ResourceLocation(ConstResources.RES_ICE_ARROW)
    }
}