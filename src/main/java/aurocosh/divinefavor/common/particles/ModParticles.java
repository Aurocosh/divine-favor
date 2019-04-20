package aurocosh.divinefavor.common.particles;

import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.particles.generic.ParticleManager;
import aurocosh.divinefavor.common.particles.generic.ParticleRenderer;
import aurocosh.divinefavor.common.particles.particles.EtherealParticle;
import aurocosh.divinefavor.common.particles.particles.ImmobileParticle;
import aurocosh.divinefavor.common.particles.particles.MobileParticle;
import aurocosh.divinefavor.common.particles.generators.EtherealParticleGenerator;
import aurocosh.divinefavor.common.particles.generators.ImmobileParticleGenerator;
import aurocosh.divinefavor.common.particles.generators.MobileParticleGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class ModParticles {
    public static EtherealParticleGenerator ethereal;
    public static MobileParticleGenerator mobile;
    public static ImmobileParticleGenerator immobile;

    public static void preInit() {
        ResourceLocation commonTexture = new ResourceLocation(ConstResources.TEX_PARTICLES);

        ParticleManager<EtherealParticle> etherealParticleManager = ParticleHandler.register(new ParticleManager<>(new ParticleRenderer<>(commonTexture), EtherealParticle::new));
        ParticleManager<MobileParticle> mobileParticleManager = ParticleHandler.register(new ParticleManager<>(new ParticleRenderer<>(commonTexture), MobileParticle::new));
        ParticleManager<ImmobileParticle> immobileParticleManager = ParticleHandler.register(new ParticleManager<>(new ParticleRenderer<>(commonTexture), ImmobileParticle::new));

        ethereal = new EtherealParticleGenerator(etherealParticleManager);
        mobile = new MobileParticleGenerator(mobileParticleManager);
        immobile = new ImmobileParticleGenerator(immobileParticleManager);
    }
}