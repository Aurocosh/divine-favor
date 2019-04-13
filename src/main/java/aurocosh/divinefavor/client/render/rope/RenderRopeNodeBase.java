package aurocosh.divinefavor.client.render.rope;

import aurocosh.divinefavor.client.models.ModelRopeNode;
import aurocosh.divinefavor.common.entity.rope.EntityRopeNodeBase;
import aurocosh.divinefavor.common.util.LightingUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderRopeNodeBase<T extends EntityRopeNodeBase> extends Render<T> {
    private Frustum frustum;

    protected static final ResourceLocation TEXTURE = new ResourceLocation("divinefavor:textures/blocks/rope_explosive.png");
    protected static final ModelRopeNode nodeModel = new ModelRopeNode();

    public RenderRopeNodeBase(RenderManager renderManager) {
        super(renderManager);
        this.frustum = new Frustum();
    }

    @Override
    public void doRender(T ropeNode, double x, double y, double z, float yaw, float partialTicks) {
        this.bindEntityTexture(ropeNode);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        GlStateManager.pushMatrix();

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.35F);
        LightingUtil.INSTANCE.setLighting(255);

        if (ropeNode.getNextNodeClient() == null)
            GlStateManager.color(0.25F, 1.0F, 0.25F, 0.35F);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        drawModel(ropeNode);
        GlStateManager.popMatrix();

        GlStateManager.color(1, 1, 1, 1);

        LightingUtil.INSTANCE.revert();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();

        double camPosX = this.interpolate(ropeNode.lastTickPosX, ropeNode.posX, partialTicks) - x;
        double camPosY = this.interpolate(ropeNode.lastTickPosY, ropeNode.posY, partialTicks) - y;
        double camPosZ = this.interpolate(ropeNode.lastTickPosZ, ropeNode.posZ, partialTicks) - z;

        this.frustum.setPosition(camPosX, camPosY, camPosZ);

        Entity prevNode = ropeNode.getPreviousNodeClient();

        if (prevNode != null) {
            if (!this.renderManager.getEntityRenderObject(prevNode).shouldRender(prevNode, this.frustum, camPosX, camPosY, camPosZ)) {
                //Previous node not rendered, render rope
                GlStateManager.pushMatrix();
                double renderOffsetX = this.interpolate(prevNode.lastTickPosX - ropeNode.lastTickPosX, prevNode.posX - ropeNode.posX, partialTicks);
                double renderOffsetY = this.interpolate(prevNode.lastTickPosY - ropeNode.lastTickPosY, prevNode.posY - ropeNode.posY, partialTicks);
                double renderOffsetZ = this.interpolate(prevNode.lastTickPosZ - ropeNode.lastTickPosZ, prevNode.posZ - ropeNode.posZ, partialTicks);
                GlStateManager.translate(renderOffsetX, renderOffsetY, renderOffsetZ);
                this.renderConnection(prevNode, ropeNode, tessellator, buffer, x, y, z, partialTicks);
                GlStateManager.popMatrix();
            }
        }

        Entity nextNode = ropeNode.getNextNodeClient();
        if (nextNode != null)
            this.renderConnection(ropeNode, nextNode, tessellator, buffer, x, y, z, partialTicks);

        GlStateManager.popMatrix();
    }

    protected void drawModel(EntityRopeNodeBase ropeNode) {
        nodeModel.render(ropeNode, 0, 0, 0, 0, 0, 0.0625F);
    }

    protected double interpolate(double prev, double now, double partialTicks) {
        return prev + (now - prev) * partialTicks;
    }

    protected void renderConnection(Entity node1, Entity node2, Tessellator tessellator, BufferBuilder buffer, double x, double y, double z, float partialTicks) {
        if (node2 != null) {
            double camPosX = this.interpolate(node1.prevPosX - x, node1.posX - x, partialTicks);
            double camPosY = this.interpolate(node1.prevPosY - y, node1.posY - y, partialTicks);
            double camPosZ = this.interpolate(node1.prevPosZ - z, node1.posZ - z, partialTicks);

            double startX = x;
            double startY = y;
            double startZ = z;
            double endX = this.interpolate(node2.prevPosX - camPosX, node2.posX - camPosX, partialTicks);
            double endY = this.interpolate(node2.prevPosY - camPosY, node2.posY - camPosY, partialTicks);
            if (node2 instanceof EntityRopeNodeBase == false) {
                endY += node2.getEyeHeight() / 2.0D;
            }
            double endZ = this.interpolate(node2.prevPosZ - camPosZ, node2.posZ - camPosZ, partialTicks);

            double diffX = (double) ((float) (endX - startX));
            double diffY = (double) ((float) (endY - startY));
            double diffZ = (double) ((float) (endZ - startZ));

            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableCull();

            buffer.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);
            for (int i = 0; i <= 24; ++i) {
                float r;
                float g;
                float b;

                if (i % 2 == 0) {
                    r = 0.1F;
                    g = 0.1F;
                    b = 0.1F;
                }
                else {
                    r = 0.3F;
                    g = 0.3F;
                    b = 0.3F;
                }

                float percentage = (float) i / 24.0F;
                double yMult = endY < startY ? percentage * Math.sqrt(percentage) : percentage * percentage;

                buffer.pos(x + diffX * (double) percentage + 0.0D, y + diffY * (double) (yMult + percentage) * 0.5D, z + diffZ * (double) percentage).color(r, g, b, 1).endVertex();
                buffer.pos(x + diffX * (double) percentage + 0.025D, y + diffY * (double) (yMult + percentage) * 0.5D + 0.025D, z + diffZ * (double) percentage).color(r, g, b, 1).endVertex();
            }
            tessellator.draw();

            buffer.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);
            for (int i = 0; i <= 24; ++i) {
                float r;
                float g;
                float b;

                if (i % 2 == 0) {
                    r = 0.1F;
                    g = 0.1F;
                    b = 0.1F;
                }
                else {
                    r = 0.3F;
                    g = 0.3F;
                    b = 0.3F;
                }

                float percentage = (float) i / 24.0F;
                double yMult = endY < startY ? percentage * Math.sqrt(percentage) : percentage * percentage;

                buffer.pos(x + diffX * (double) percentage + 0.0D, y + diffY * (double) (yMult + percentage) * 0.5D + 0.025D, z + diffZ * (double) percentage).color(r, g, b, 1).endVertex();
                buffer.pos(x + diffX * (double) percentage + 0.025D, y + diffY * (double) (yMult + percentage) * 0.5D, z + diffZ * (double) percentage + 0.025D).color(r, g, b, 1).endVertex();
            }
            tessellator.draw();

            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            GlStateManager.enableCull();
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityRopeNodeBase entity) {
        return TEXTURE;
    }
}