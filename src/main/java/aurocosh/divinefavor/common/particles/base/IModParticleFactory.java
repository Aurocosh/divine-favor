package aurocosh.divinefavor.common.particles.base;

import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface IModParticleFactory<T extends ModParticle> {
    @Nullable
    T createParticle(World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... vars);
}
