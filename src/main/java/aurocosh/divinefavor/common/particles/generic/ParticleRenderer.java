package aurocosh.divinefavor.common.particles.generic;

import aurocosh.divinefavor.common.particles.base.IParticleRenderer;
import aurocosh.divinefavor.common.particles.base.ModParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ParticleRenderer<T extends ModParticle> implements IParticleRenderer<T> {
    private final ResourceLocation texture;

    public ParticleRenderer(ResourceLocation texture) {
        this.texture = texture;
    }

    @Override
    public void renderParticles(Iterable<T> particles, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        if (player == null)
            return;

        float x = ActiveRenderInfo.getRotationX();
        float z = ActiveRenderInfo.getRotationZ();
        float yz = ActiveRenderInfo.getRotationYZ();
        float xy = ActiveRenderInfo.getRotationXY();
        float xz = ActiveRenderInfo.getRotationXZ();

        Particle.interpPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        Particle.interpPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        Particle.interpPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
        Particle.cameraViewDir = player.getLook(partialTicks);

        GlStateManager.pushMatrix();

        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.alphaFunc(516, 0.003921569F);
        GlStateManager.disableCull();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);

        GlStateManager.depthMask(false);

        mc.getTextureManager().bindTexture(texture);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        for (ModParticle particle : particles)
            particle.renderParticle(buffer, player, partialTicks, x, xz, z, yz, xy);
        tessellator.draw();

        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(516, 0.1F);

        GlStateManager.popMatrix();
    }
}
