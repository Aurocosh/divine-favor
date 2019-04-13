package aurocosh.divinefavor.client.render.rope;

import aurocosh.divinefavor.client.models.rope.ModelRopeBarrierNode;
import aurocosh.divinefavor.common.entity.rope.EntityRopeBarrierNode;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.vecmath.Color4f;

public class RenderRopeBarrierNode extends RenderRopeNodeBase<EntityRopeBarrierNode> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation("divinefavor:textures/blocks/rope_nodes.png");
    protected static final ModelRopeBarrierNode nodeModel = new ModelRopeBarrierNode();

    public RenderRopeBarrierNode(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void drawModel(EntityRopeBarrierNode ropeNode, float partialTicks) {
        nodeModel.render(ropeNode, 0, 0, 0, 0, 0, 0.0625F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityRopeBarrierNode entity) {
        return TEXTURE;
    }

    @Override
    protected Color4f getConnectionColor() {
        return new Color4f(0.7f, 0.3f, 0.7f, 0.7f);
    }

    @Override
    protected Color4f getConnectionSubColor() {
        return new Color4f(0.4f, 0.2f, 0.4f, 0.7f);
    }
}