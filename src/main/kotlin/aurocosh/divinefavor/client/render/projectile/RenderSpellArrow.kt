package aurocosh.divinefavor.client.render.projectile

import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType
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
class RenderSpellArrow(manager: RenderManager) : RenderArrow<EntitySpellArrow>(manager) {

    override fun doRender(entity: EntitySpellArrow, x: Double, y: Double, z: Double, entityYaw: Float, partialTicks: Float) {
        this.bindEntityTexture(entity)

        val color = entity.color
        GlStateManager.color(color.x, color.y, color.z, 1.0f)
        GlStateManager.pushMatrix()
        GlStateManager.disableLighting()
        GlStateManager.translate(x.toFloat(), y.toFloat(), z.toFloat())
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0f, 0.0f, 1.0f, 0.0f)
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0f, 0.0f, 1.0f)
        val tessellator = Tessellator.getInstance()
        val bufferBuilder = tessellator.buffer

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
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX)
        bufferBuilder.pos(-7.0, -2.0, -2.0).tex(0.0, 0.15625).endVertex()
        bufferBuilder.pos(-7.0, -2.0, 2.0).tex(0.15625, 0.15625).endVertex()
        bufferBuilder.pos(-7.0, 2.0, 2.0).tex(0.15625, 0.3125).endVertex()
        bufferBuilder.pos(-7.0, 2.0, -2.0).tex(0.0, 0.3125).endVertex()
        tessellator.draw()
        GlStateManager.glNormal3f(-0.05625f, 0.0f, 0.0f)
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX)
        bufferBuilder.pos(-7.0, 2.0, -2.0).tex(0.0, 0.15625).endVertex()
        bufferBuilder.pos(-7.0, 2.0, 2.0).tex(0.15625, 0.15625).endVertex()
        bufferBuilder.pos(-7.0, -2.0, 2.0).tex(0.15625, 0.3125).endVertex()
        bufferBuilder.pos(-7.0, -2.0, -2.0).tex(0.0, 0.3125).endVertex()
        tessellator.draw()

        for (j in 0..3) {
            GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f)
            GlStateManager.glNormal3f(0.0f, 0.0f, 0.05625f)
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX)
            bufferBuilder.pos(-8.0, -2.0, 0.0).tex(0.0, 0.0).endVertex()
            bufferBuilder.pos(8.0, -2.0, 0.0).tex(0.5, 0.0).endVertex()
            bufferBuilder.pos(8.0, 2.0, 0.0).tex(0.5, 0.15625).endVertex()
            bufferBuilder.pos(-8.0, 2.0, 0.0).tex(0.0, 0.15625).endVertex()
            tessellator.draw()
        }

        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode()
            GlStateManager.disableColorMaterial()
        }

        GlStateManager.disableRescaleNormal()
        GlStateManager.enableLighting()
        GlStateManager.popMatrix()

        if (!this.renderOutlines)
            this.renderName(entity, x, y, z)
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    override fun getEntityTexture(entity: EntitySpellArrow): ResourceLocation? {
        return when (entity.arrowType) {
            ArrowType.WOODEN_ARROW -> RES_ARROW
            ArrowType.SPELL_ARROW -> RES_SPELL_ARROW
            ArrowType.CURSED_ARROW -> RES_CURSED_ARROW
            else -> RES_ARROW
        }
    }

    companion object {
        val RES_ARROW = ResourceLocation(ConstResources.RES_ARROW)
        val RES_SPELL_ARROW = ResourceLocation(ConstResources.RES_SPELL_ARROW)
        val RES_CURSED_ARROW = ResourceLocation(ConstResources.RES_CURSED_ARROW)
    }
}