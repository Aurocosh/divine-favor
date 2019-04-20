package aurocosh.divinefavor.common.particles.generators;

import aurocosh.divinefavor.common.particles.base.ParticleGenerator;
import aurocosh.divinefavor.common.particles.generic.ParticleManager;
import aurocosh.divinefavor.common.particles.particles.ImmobileParticle;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.vecmath.Color3f;

public class ImmobileParticleGenerator extends ParticleGenerator<ImmobileParticle> {
    public ImmobileParticleGenerator(ParticleManager<ImmobileParticle> particleManager) {
        super(particleManager);
    }

    public void createParticle(World world, Vec3d position, Color3f color3f, int minLife, int maxLife) {
        if (!canCreateParticle(position))
            return;
        ImmobileParticle particle = particleManager.getParticle();
        particle.init(world, position, color3f, minLife, maxLife);
        particleManager.addParticle(particle);
    }
}
