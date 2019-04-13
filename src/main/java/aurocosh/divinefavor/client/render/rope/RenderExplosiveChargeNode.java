package aurocosh.divinefavor.client.render.rope;

import aurocosh.divinefavor.client.models.ModelRopeNode;
import aurocosh.divinefavor.common.entity.rope.EntityExplosiveChargeNode;
import aurocosh.divinefavor.common.entity.rope.EntityRopeNodeBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderExplosiveChargeNode extends RenderRopeNodeBase<EntityExplosiveChargeNode> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation("divinefavor:textures/blocks/explosive_charge.png");
    protected static final ModelRopeNode nodeModel = new ModelRopeNode();

    public RenderExplosiveChargeNode(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void drawModel(EntityRopeNodeBase ropeNode) {
        nodeModel.render(ropeNode, 0, 0, 0, 0, 0, 0.0625F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityExplosiveChargeNode entity) {
        return TEXTURE;
    }
}