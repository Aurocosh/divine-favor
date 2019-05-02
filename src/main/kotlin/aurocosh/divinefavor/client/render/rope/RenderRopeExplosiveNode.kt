package aurocosh.divinefavor.client.render.rope

import aurocosh.divinefavor.client.models.rope.ModelRopeExplosiveNode
import aurocosh.divinefavor.client.render.rope.base.RenderRopeNodeBase
import aurocosh.divinefavor.common.entity.rope.EntityRopeExplosiveNode
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.MathHelper
import javax.vecmath.Color4f

class RenderRopeExplosiveNode(renderManager: RenderManager) : RenderRopeNodeBase<EntityRopeExplosiveNode>(renderManager) {

    override val normalColor: Color4f
        get() = Color4f(1f, 1f, 1f, 1f)

    override val lastColor: Color4f
        get() = Color4f(1f, 0.25f, 0.25f, 1f)

    override val connectionColor: Color4f
        get() = Color4f(0.7f, 0.3f, 0.3f, 0.7f)

    override val connectionSubColor: Color4f
        get() = Color4f(0.6f, 0.3f, 0.3f, 0.7f)

    override fun drawModel(ropeNode: EntityRopeExplosiveNode, partialTicks: Float) {
        GlStateManager.pushMatrix()

        if (ropeNode.fuse.toFloat() - partialTicks + 1.0f < 10.0f) {
            var f = 1.0f - (ropeNode.fuse.toFloat() - partialTicks + 1.0f) / 10.0f
            f = MathHelper.clamp(f, 0.0f, 1.0f)
            f *= f
            f *= f
            val f1 = 1.0f + f * 0.3f
            GlStateManager.scale(f1, f1, f1)
        }
        nodeModel.render(ropeNode, 0f, 0f, 0f, 0f, 0f, 0.0625f)

        val f2 = (1.0f - (ropeNode.fuse.toFloat() - partialTicks + 1.0f) / 100.0f) * 0.8f

        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial()
            GlStateManager.enableOutlineMode(this.getTeamColor(ropeNode))
            nodeModel.render(ropeNode, 0f, 0f, 0f, 0f, 0f, 0.0625f)
            GlStateManager.disableOutlineMode()
            GlStateManager.disableColorMaterial()
        } else if (ropeNode.fuse / 5 % 2 == 0) {
            GlStateManager.disableTexture2D()
            GlStateManager.disableLighting()
            GlStateManager.enableBlend()
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA)
            GlStateManager.color(1.0f, 1.0f, 1.0f, f2)
            GlStateManager.doPolygonOffset(-3.0f, -3.0f)
            GlStateManager.enablePolygonOffset()
            nodeModel.render(ropeNode, 0f, 0f, 0f, 0f, 0f, 0.0625f)
            GlStateManager.doPolygonOffset(0.0f, 0.0f)
            GlStateManager.disablePolygonOffset()
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
            GlStateManager.disableBlend()
            GlStateManager.enableLighting()
            GlStateManager.enableTexture2D()
        }

        GlStateManager.popMatrix()
    }

    override fun getEntityTexture(p0: EntityRopeExplosiveNode): ResourceLocation? {
        return TEXTURE
    }

    companion object {
        private val TEXTURE = ResourceLocation("divinefavor:textures/blocks/rope_nodes.png")
        private val nodeModel = ModelRopeExplosiveNode()
    }
}