package aurocosh.divinefavor.common.particles.particles;

import aurocosh.divinefavor.common.particles.base.IModParticleFactory;
import aurocosh.divinefavor.common.particles.base.ModParticle;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class ParticleStatic extends ModParticle {
    private final float portalParticleScale;

    protected ParticleStatic(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double minLife, double maxLife, double zSpeedIn) {
        super(worldIn, xCoordIn, minLife, zCoordIn, 0, 0, 0);
        posX = xCoordIn;
        posY = yCoordIn;
        posZ = zCoordIn;

        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        float f = rand.nextFloat() * 0.6F + 0.4F;
        particleScale = rand.nextFloat() * 0.2F + 0.5F;
        portalParticleScale = particleScale;
        particleRed = f * 0.9F;
        particleGreen = f * 0.3F;
        particleBlue = f;
        setParticleTextureIndex((int) (Math.random() * 8.0D));

        particleMaxAge = UtilRandom.nextInt((int) minLife, (int) maxLife);
    }


    @Override
    public void onUpdate() {
        if (particleAge++ >= particleMaxAge)
            setExpired();
    }

    /**
     * Renders the particle
     */
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
    }

    public int getBrightnessForRender(float partialTick) {
        int i = super.getBrightnessForRender(partialTick);
        float f = (float) particleAge / (float) particleMaxAge;
        f = f * f;
        f = f * f;
        int j = i & 255;
        int k = i >> 16 & 255;
        k = k + (int) (f * 15.0F * 16.0F);

        if (k > 240) {
            k = 240;
        }

        return j | k << 16;
    }

    @SideOnly(Side.CLIENT)
    public static class ParticleStaticactory implements IModParticleFactory<ParticleStatic> {
        @Nullable
        @Override
        public ParticleStatic createParticle(World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... vars) {
            return new ParticleStatic(world, xCoord, yCoord, zCoord, (float) xSpeed, (float) ySpeed, (float) zSpeed);
        }
    }
}