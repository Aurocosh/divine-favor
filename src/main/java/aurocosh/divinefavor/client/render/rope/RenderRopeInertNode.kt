package aurocosh.divinefavor.client.render.rope

import aurocosh.divinefavor.client.models.rope.ModelRopeInertNode
import aurocosh.divinefavor.client.render.rope.base.RenderRopeNodeBase
import aurocosh.divinefavor.common.entity.rope.EntityRopeInertNode
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.ResourceLocation

class RenderRopeInertNode(renderManager: RenderManager) : RenderRopeNodeBase<EntityRopeInertNode>(renderManager) {

    override fun drawModel(ropeNode: EntityRopeInertNode, partialTicks: Float) {
        nodeModel.render(ropeNode, 0f, 0f, 0f, 0f, 0f, 0.0625f)
    }

    override fun getEntityTexture(p0: EntityRopeInertNode): ResourceLocation? {
        return TEXTURE
    }

    companion object {
        private val TEXTURE = ResourceLocation("divinefavor:textures/blocks/rope_nodes.png")
        private val nodeModel = ModelRopeInertNode()
    }
}