package aurocosh.divinefavor.common.particles

import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.particles.base.ModParticle
import aurocosh.divinefavor.common.particles.base.ParticleGenerator
import aurocosh.divinefavor.common.particles.generic.ParticleManager
import aurocosh.divinefavor.common.particles.generic.ParticleRendererNoDepth
import aurocosh.divinefavor.common.particles.generic.ParticleRendererNormal
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
object ModParticles {
    lateinit var normal: ParticleGenerator<ModParticle>
    lateinit var noDepth: ParticleGenerator<ModParticle>

    fun preInit() {
        val commonTexture = ResourceLocation(ConstResources.TEX_PARTICLES)
        normal = ParticleGenerator(ParticleHandler.register(ParticleManager(ConfigGeneral.particleLimit, ParticleRendererNormal(commonTexture))))
        noDepth = ParticleGenerator(ParticleHandler.register(ParticleManager(ConfigGeneral.particleLimit, ParticleRendererNoDepth(commonTexture))))
    }
}