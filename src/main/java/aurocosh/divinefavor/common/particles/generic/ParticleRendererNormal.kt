package aurocosh.divinefavor.common.particles.generic

import aurocosh.divinefavor.common.particles.base.IParticleRenderer
import aurocosh.divinefavor.common.particles.base.ModParticle
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.ActiveRenderInfo
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

class ParticleRendererNormal<T : ModParticle>(private val texture: ResourceLocation) : IParticleRenderer<T> {

    override fun renderParticles(particles: Iterable<T>, partialTicks: Float) {
        val mc = Minecraft.getMinecraft()
        val player = mc.player ?: return

        val x = ActiveRenderInfo.getRotationX()
        val z = ActiveRenderInfo.getRotationZ()
        val yz = ActiveRenderInfo.getRotationYZ()
        val xy = ActiveRenderInfo.getRotationXY()
        val xz = ActiveRenderInfo.getRotationXZ()
        //
        //        Particle.interpPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        //        Particle.interpPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        //        Particle.interpPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
        //        Particle.cameraViewDir = player.getLook(partialTicks);

        GlStateManager.pushMatrix()

        GlStateManager.enableAlpha()
        GlStateManager.enableBlend()
        GlStateManager.alphaFunc(516, 0.003921569f)
        GlStateManager.disableCull()
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE)
        GlStateManager.depthMask(false)

        mc.textureManager.bindTexture(texture)
        val tessellator = Tessellator.getInstance()
        val buffer = tessellator.buffer

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP)
        for (particle in particles)
            particle.renderParticle(buffer, player, partialTicks, x, xz, z, yz, xy)
        tessellator.draw()

        GlStateManager.enableCull()
        GlStateManager.depthMask(true)
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA)
        GlStateManager.disableBlend()
        GlStateManager.alphaFunc(516, 0.1f)

        GlStateManager.popMatrix()
    }
}
