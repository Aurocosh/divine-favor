package aurocosh.divinefavor.common.particles.generators;

import aurocosh.divinefavor.common.particles.base.ParticleGenerator;
import aurocosh.divinefavor.common.particles.generic.ParticleManager;
import aurocosh.divinefavor.common.particles.particles.EtherealParticle;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EtherealParticleGenerator extends ParticleGenerator<EtherealParticle> {
    public EtherealParticleGenerator(ParticleManager<EtherealParticle> particleManager) {
        super(particleManager);
    }

    public void createParticle(World world, Vec3d position, float scale, float red) {
        if (!canCreateParticle(position))
            return;
        EtherealParticle particle = particleManager.getParticle();
        particle.init(world, position, scale, red);
        particleManager.addParticle(particle);
    }
}
