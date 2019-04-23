package aurocosh.divinefavor.client.render;

import aurocosh.divinefavor.client.models.ModelPing;
import aurocosh.divinefavor.common.entity.EntityPing;
import aurocosh.divinefavor.common.util.UtilLighting;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderPing extends Render<EntityPing> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation("divinefavor:textures/blocks/rope_nodes.png");
    protected static final ModelPing MODEL_PING = new ModelPing();

    public RenderPing(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityPing ping, double x, double y, double z, float yaw, float partialTicks) {
        bindEntityTexture(ping);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        GlStateManager.pushMatrix();

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();

        GlStateManager.color(1, 1, 1, 1);
        UtilLighting.INSTANCE.setLighting(255);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        drawModel(ping, partialTicks);
        GlStateManager.popMatrix();

        UtilLighting.INSTANCE.revert();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();

        GlStateManager.popMatrix();
    }

    protected void drawModel(EntityPing entityPing, float partialTicks) {
        MODEL_PING.render(entityPing, 0, 0, 0, 0, 0, 0.0625F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPing ping) {
        return TEXTURE;
    }
}