package aurocosh.divinefavor.common.particles;

import aurocosh.divinefavor.common.config.common.ConfigGeneral;
import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.particles.base.ModParticle;
import aurocosh.divinefavor.common.particles.base.ParticleGenerator;
import aurocosh.divinefavor.common.particles.generic.ParticleManager;
import aurocosh.divinefavor.common.particles.generic.ParticleRendererNoDepth;
import aurocosh.divinefavor.common.particles.generic.ParticleRendererNormal;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class ModParticles {
    public static ParticleGenerator<ModParticle> normal;
    public static ParticleGenerator<ModParticle> noDepth;

    public static void preInit() {
        ResourceLocation commonTexture = new ResourceLocation(ConstResources.TEX_PARTICLES);
        normal = new ParticleGenerator<>(ParticleHandler.register(new ParticleManager<>(ConfigGeneral.particleLimit, new ParticleRendererNormal<>(commonTexture))));
        noDepth = new ParticleGenerator<>(ParticleHandler.register(new ParticleManager<>(ConfigGeneral.particleLimit, new ParticleRendererNoDepth<>(commonTexture))));
    }
}