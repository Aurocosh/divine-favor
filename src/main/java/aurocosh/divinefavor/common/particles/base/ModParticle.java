package aurocosh.divinefavor.common.particles.base;

import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ModParticle extends Particle {
    protected ModParticle(World worldIn, double posXIn, double posYIn, double posZIn) {
        super(worldIn, posXIn, posYIn, posZIn);
    }

    public ModParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
    }

    public boolean isDead() {
        return isExpired;
    }

    public Vec3d getPositionVector() {
        return new Vec3d(posX, posY, posZ);
    }
}
