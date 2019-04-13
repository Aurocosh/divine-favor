package aurocosh.divinefavor.client.render.rope;

import aurocosh.divinefavor.client.models.rope.ModelRopeGuideNode;
import aurocosh.divinefavor.client.render.rope.base.RenderRopeNodeBase;
import aurocosh.divinefavor.common.entity.rope.EntityRopeGuideNode;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.vecmath.Color4f;

public class RenderRopeGuideNode extends RenderRopeNodeBase<EntityRopeGuideNode> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation("divinefavor:textures/blocks/rope_nodes.png");
    protected static final ModelRopeGuideNode nodeModel = new ModelRopeGuideNode();

    public RenderRopeGuideNode(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void drawModel(EntityRopeGuideNode ropeNode, float partialTicks) {
        nodeModel.render(ropeNode, 0, 0, 0, 0, 0, 0.0625F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityRopeGuideNode entity) {
        return TEXTURE;
    }

    @Override
    protected Color4f getConnectionColor() {
        return new Color4f(0.7f, 0.7f, 0.2f, 0.7f);
    }

    @Override
    protected Color4f getConnectionSubColor() {
        return new Color4f(0.5f, 0.5f, 0.2f, 0.7f);
    }
}