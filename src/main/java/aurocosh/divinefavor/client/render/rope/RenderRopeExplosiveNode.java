package aurocosh.divinefavor.client.render.rope;

import aurocosh.divinefavor.client.models.ModelRopeExplosiveNode;
import aurocosh.divinefavor.common.entity.rope.EntityRopeExplosiveNode;
import aurocosh.divinefavor.common.entity.rope.EntityRopeNodeBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderRopeExplosiveNode extends RenderRopeNodeBase<EntityRopeExplosiveNode> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation("divinefavor:textures/blocks/rope_nodes.png");
    protected static final ModelRopeExplosiveNode nodeModel = new ModelRopeExplosiveNode();

    public RenderRopeExplosiveNode(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void drawModel(EntityRopeNodeBase ropeNode) {
        nodeModel.render(ropeNode, 0, 0, 0, 0, 0, 0.0625F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityRopeExplosiveNode entity) {
        return TEXTURE;
    }
}