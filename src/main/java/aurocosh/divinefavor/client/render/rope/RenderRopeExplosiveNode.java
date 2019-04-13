package aurocosh.divinefavor.client.render.rope;

import aurocosh.divinefavor.client.models.ModelRopeExplosiveNode;
import aurocosh.divinefavor.common.entity.rope.EntityRopeExplosiveNode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.vecmath.Color4f;

public class RenderRopeExplosiveNode extends RenderRopeNodeBase<EntityRopeExplosiveNode> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation("divinefavor:textures/blocks/rope_nodes.png");
    protected static final ModelRopeExplosiveNode nodeModel = new ModelRopeExplosiveNode();

    public RenderRopeExplosiveNode(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void drawModel(EntityRopeExplosiveNode ropeNode, float partialTicks) {
        GlStateManager.pushMatrix();

        if ((float) ropeNode.getFuse() - partialTicks + 1.0F < 10.0F) {
            float f = 1.0F - ((float) ropeNode.getFuse() - partialTicks + 1.0F) / 10.0F;
            f = MathHelper.clamp(f, 0.0F, 1.0F);
            f = f * f;
            f = f * f;
            float f1 = 1.0F + f * 0.3F;
            GlStateManager.scale(f1, f1, f1);
        }
        nodeModel.render(ropeNode, 0, 0, 0, 0, 0, 0.0625F);

        float f2 = (1.0F - ((float)ropeNode.getFuse() - partialTicks + 1.0F) / 100.0F) * 0.8F;

        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(ropeNode));
            nodeModel.render(ropeNode, 0, 0, 0, 0, 0, 0.0625F);
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        else if (ropeNode.getFuse() / 5 % 2 == 0)
        {
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
            GlStateManager.color(1.0F, 1.0F, 1.0F, f2);
            GlStateManager.doPolygonOffset(-3.0F, -3.0F);
            GlStateManager.enablePolygonOffset();
            nodeModel.render(ropeNode, 0, 0, 0, 0, 0, 0.0625F);
            GlStateManager.doPolygonOffset(0.0F, 0.0F);
            GlStateManager.disablePolygonOffset();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
        }

        GlStateManager.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityRopeExplosiveNode entity) {
        return TEXTURE;
    }

    @Override
    protected Color4f getNormalColor() {
        return new Color4f(1, 1, 1, 1);
    }

    @Override
    protected Color4f getLastColor() {
        return new Color4f(1, 0.25f, 0.25f, 1);
    }
}