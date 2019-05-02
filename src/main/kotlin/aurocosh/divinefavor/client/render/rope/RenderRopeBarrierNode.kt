package aurocosh.divinefavor.client.render.rope

import aurocosh.divinefavor.client.models.rope.ModelRopeBarrierNode
import aurocosh.divinefavor.client.render.rope.base.RenderRopeNodeBase
import aurocosh.divinefavor.common.entity.rope.EntityRopeBarrierNode
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.ResourceLocation

import javax.vecmath.Color4f

class RenderRopeBarrierNode(renderManager: RenderManager) : RenderRopeNodeBase<EntityRopeBarrierNode>(renderManager) {

    override val connectionColor: Color4f
        get() = Color4f(0.7f, 0.3f, 0.7f, 0.7f)

    override val connectionSubColor: Color4f
        get() = Color4f(0.4f, 0.2f, 0.4f, 0.7f)

    override fun drawModel(ropeNode: EntityRopeBarrierNode, partialTicks: Float) {
        nodeModel.render(ropeNode, 0f, 0f, 0f, 0f, 0f, 0.0625f)
    }

    override fun getEntityTexture(p0: EntityRopeBarrierNode): ResourceLocation? {
        return TEXTURE
    }

    companion object {
        private val TEXTURE = ResourceLocation("divinefavor:textures/blocks/rope_nodes.png")
        private val nodeModel = ModelRopeBarrierNode()
    }
}