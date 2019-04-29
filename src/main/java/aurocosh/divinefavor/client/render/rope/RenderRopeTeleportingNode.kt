package aurocosh.divinefavor.client.render.rope

import aurocosh.divinefavor.client.models.rope.ModelRopeTeleportingNode
import aurocosh.divinefavor.client.render.rope.base.RenderRopeNodeBase
import aurocosh.divinefavor.common.entity.rope.EntityRopeTeleportingNode
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.ResourceLocation

import javax.vecmath.Color4f

class RenderRopeTeleportingNode(renderManager: RenderManager) : RenderRopeNodeBase<EntityRopeTeleportingNode>(renderManager) {

    override val connectionColor: Color4f
        get() = Color4f(0.3f, 0.9f, 0.4f, 0.7f)

    override val connectionSubColor: Color4f
        get() = Color4f(0.3f, 0.7f, 0.4f, 0.7f)

    override fun drawModel(ropeNode: EntityRopeTeleportingNode, partialTicks: Float) {
        nodeModel.render(ropeNode, 0f, 0f, 0f, 0f, 0f, 0.0625f)
    }

    override fun getEntityTexture(p0: EntityRopeTeleportingNode): ResourceLocation? {
        return TEXTURE
    }

    companion object {
        private val TEXTURE = ResourceLocation("divinefavor:textures/blocks/rope_nodes.png")
        private val nodeModel = ModelRopeTeleportingNode()
    }
}