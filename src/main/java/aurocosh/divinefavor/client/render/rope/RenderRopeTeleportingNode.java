package aurocosh.divinefavor.client.render.rope;

import aurocosh.divinefavor.client.models.rope.ModelRopeTeleportingNode;
import aurocosh.divinefavor.client.render.rope.base.RenderRopeNodeBase;
import aurocosh.divinefavor.common.entity.rope.EntityRopeTeleportingNode;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.vecmath.Color4f;

public class RenderRopeTeleportingNode extends RenderRopeNodeBase<EntityRopeTeleportingNode> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation("divinefavor:textures/blocks/rope_nodes.png");
    protected static final ModelRopeTeleportingNode nodeModel = new ModelRopeTeleportingNode();

    public RenderRopeTeleportingNode(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void drawModel(EntityRopeTeleportingNode ropeNode, float partialTicks) {
        nodeModel.render(ropeNode, 0, 0, 0, 0, 0, 0.0625F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityRopeTeleportingNode entity) {
        return TEXTURE;
    }

    @Override
    protected Color4f getConnectionColor() {
        return new Color4f(0.3f, 0.9f, 0.4f, 0.7f);
    }

    @Override
    protected Color4f getConnectionSubColor() {
        return new Color4f(0.3f, 0.7f, 0.4f, 0.7f);
    }
}