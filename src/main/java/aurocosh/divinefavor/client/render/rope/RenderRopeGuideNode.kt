package aurocosh.divinefavor.client.render.rope

import aurocosh.divinefavor.client.models.rope.ModelRopeGuideNode
import aurocosh.divinefavor.client.render.rope.base.RenderRopeNodeBase
import aurocosh.divinefavor.common.entity.rope.EntityRopeGuideNode
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.ResourceLocation

import javax.vecmath.Color4f

class RenderRopeGuideNode(renderManager: RenderManager) : RenderRopeNodeBase<EntityRopeGuideNode>(renderManager) {

    override val connectionColor: Color4f
        get() = Color4f(0.7f, 0.7f, 0.2f, 0.7f)

    override val connectionSubColor: Color4f
        get() = Color4f(0.5f, 0.5f, 0.2f, 0.7f)

    override fun drawModel(ropeNode: EntityRopeGuideNode, partialTicks: Float) {
        nodeModel.render(ropeNode, 0f, 0f, 0f, 0f, 0f, 0.0625f)
    }

    override fun getEntityTexture(p0: EntityRopeGuideNode): ResourceLocation? {
        return TEXTURE
    }

    companion object {
        private val TEXTURE = ResourceLocation("divinefavor:textures/blocks/rope_nodes.png")
        private val nodeModel = ModelRopeGuideNode()
    }
}