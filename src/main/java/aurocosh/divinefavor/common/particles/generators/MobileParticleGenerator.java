package aurocosh.divinefavor.common.particles.generators;

import aurocosh.divinefavor.common.particles.base.ParticleGenerator;
import aurocosh.divinefavor.common.particles.generic.ParticleManager;
import aurocosh.divinefavor.common.particles.particles.MobileParticle;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.vecmath.Color3f;

public class MobileParticleGenerator extends ParticleGenerator<MobileParticle> {
    public MobileParticleGenerator(ParticleManager<MobileParticle> particleManager) {
        super(particleManager);
    }

    public void createParticle(World world, Vec3d position, Vec3d motion, Color3f color3f) {
        if (!canCreateParticle(position))
            return;
        MobileParticle particle = particleManager.getParticle();
        particle.init(world, position, motion, color3f);
        particleManager.addParticle(particle);
    }
}
