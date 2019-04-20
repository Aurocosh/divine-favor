package aurocosh.divinefavor.common.particles.base;

import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class ModParticle extends Particle {
    private static final AxisAlignedBB EMPTY_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

    protected ModParticle() {
        super(null, 0, 0, 0);
    }

    private ModParticle(World worldIn, double posXIn, double posYIn, double posZIn) {
        super(worldIn, posXIn, posYIn, posZIn);
    }

    private ModParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
    }

    public boolean isDead() {
        return isExpired;
    }

    protected void init(World world, Vec3d position) {
        setBoundingBox(EMPTY_AABB);
        this.width = 0.6F;
        this.height = 1.8F;
        this.rand = new Random();
        this.particleAlpha = 1.0F;
        this.world = world;
        this.setSize(0.2F, 0.2F);
        this.setPosition(position.x, position.y, position.z);
        this.prevPosX = position.x;
        this.prevPosY = position.y;
        this.prevPosZ = position.z;
        this.particleRed = 1.0F;
        this.particleGreen = 1.0F;
        this.particleBlue = 1.0F;
        this.particleTextureJitterX = this.rand.nextFloat() * 3.0F;
        this.particleTextureJitterY = this.rand.nextFloat() * 3.0F;
        this.particleScale = (this.rand.nextFloat() * 0.5F + 0.5F) * 2.0F;
        this.particleMaxAge = (int) (4.0F / (this.rand.nextFloat() * 0.9F + 0.1F));
        this.particleAge = 0;
        this.canCollide = true;
    }

    protected void init(World worldIn, Vec3d position, Vec3d motion) {
        init(worldIn, position);
        this.motionX = motion.x + (Math.random() * 2.0D - 1.0D) * 0.4000000059604645D;
        this.motionY = motion.y + (Math.random() * 2.0D - 1.0D) * 0.4000000059604645D;
        this.motionZ = motion.z + (Math.random() * 2.0D - 1.0D) * 0.4000000059604645D;
        float f = (float) (Math.random() + Math.random() + 1.0D) * 0.15F;
        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.motionX = this.motionX / (double) f1 * (double) f * 0.4000000059604645D;
        this.motionY = this.motionY / (double) f1 * (double) f * 0.4000000059604645D + 0.10000000149011612D;
        this.motionZ = this.motionZ / (double) f1 * (double) f * 0.4000000059604645D;
    }
}
