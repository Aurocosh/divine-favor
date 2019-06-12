package aurocosh.divinefavor.client.render.rope

import aurocosh.divinefavor.client.models.rope.ModelRopeLuminousNode
import aurocosh.divinefavor.client.render.rope.base.RenderRopeNodeBase
import aurocosh.divinefavor.common.entity.rope.EntityRopeLuminousNode
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.ResourceLocation
import javax.vecmath.Color4f

class RenderRopeLuminousNode(renderManager: RenderManager) : RenderRopeNodeBase<EntityRopeLuminousNode>(renderManager) {

    override val connectionColor: Color4f
        get() = Color4f(0.7f, 0.4f, 0.4f, 0.7f)

    override val connectionSubColor: Color4f
        get() = Color4f(0.7f, 0.1f, 0.1f, 0.7f)

    override fun drawModel(ropeNode: EntityRopeLuminousNode, partialTicks: Float) {
        nodeModel.render(ropeNode, 0f, 0f, 0f, 0f, 0f, 0.0625f)
    }

    override fun getEntityTexture(p0: EntityRopeLuminousNode): ResourceLocation? {
        return TEXTURE
    }

    companion object {
        private val TEXTURE = ResourceLocation("divinefavor:textures/blocks/rope_nodes.png")
        private val nodeModel = ModelRopeLuminousNode()
    }
}