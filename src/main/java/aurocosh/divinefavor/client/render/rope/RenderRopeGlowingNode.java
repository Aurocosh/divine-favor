package aurocosh.divinefavor.client.render.rope;

import aurocosh.divinefavor.client.models.rope.ModelRopeGlowingNode;
import aurocosh.divinefavor.client.render.rope.base.RenderRopeNodeBase;
import aurocosh.divinefavor.common.entity.rope.EntityRopeGlowingNode;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.vecmath.Color4f;

public class RenderRopeGlowingNode extends RenderRopeNodeBase<EntityRopeGlowingNode> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation("divinefavor:textures/blocks/rope_nodes.png");
    protected static final ModelRopeGlowingNode nodeModel = new ModelRopeGlowingNode();

    public RenderRopeGlowingNode(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void drawModel(EntityRopeGlowingNode ropeNode, float partialTicks) {
        nodeModel.render(ropeNode, 0, 0, 0, 0, 0, 0.0625F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityRopeGlowingNode entity) {
        return TEXTURE;
    }

    @Override
    protected Color4f getConnectionColor() {
        return new Color4f(0.9f, 0.9f, 0.9f, 0.7f);
    }

    @Override
    protected Color4f getConnectionSubColor() {
        return new Color4f(0.7f, 0.7f, 0.7f, 0.7f);
    }
}