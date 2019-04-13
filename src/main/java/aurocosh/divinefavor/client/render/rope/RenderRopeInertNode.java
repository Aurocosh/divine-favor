package aurocosh.divinefavor.client.render.rope;

import aurocosh.divinefavor.client.models.ModelRopeInertNode;
import aurocosh.divinefavor.common.entity.rope.EntityRopeInertNode;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderRopeInertNode extends RenderRopeNodeBase<EntityRopeInertNode> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation("divinefavor:textures/blocks/rope_nodes.png");
    protected static final ModelRopeInertNode nodeModel = new ModelRopeInertNode();

    public RenderRopeInertNode(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void drawModel(EntityRopeInertNode ropeNode, float partialTicks) {
        nodeModel.render(ropeNode, 0, 0, 0, 0, 0, 0.0625F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityRopeInertNode entity) {
        return TEXTURE;
    }
}