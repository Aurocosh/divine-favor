package aurocosh.divinefavor.common.particles.base;

import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ModParticle extends Particle {
    protected ModParticle(World world, Vec3d position) {
        super(world, position.x, position.y, position.z);
    }

    protected ModParticle(World worldIn, Vec3d position, Vec3d motion) {
        super(worldIn, position.x, position.y, position.z, motion.x, motion.y, motion.z);
    }

    public boolean isDead() {
        return isExpired;
    }
}
