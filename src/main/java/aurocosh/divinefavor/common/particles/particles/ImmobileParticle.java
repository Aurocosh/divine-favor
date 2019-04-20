package aurocosh.divinefavor.common.particles.particles;

import aurocosh.divinefavor.common.particles.base.ModParticle;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Color3f;

@SideOnly(Side.CLIENT)
public class ImmobileParticle extends ModParticle {
    public void init(World worldIn, Vec3d position, Color3f color3f, int minLife, int maxLife) {
        init(worldIn, position);
        posX = position.x;
        posY = position.y;
        posZ = position.z;

        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        float intensity = rand.nextFloat() * 0.6F + 0.4F;
        particleRed = color3f.x * intensity;
        particleGreen = color3f.y * intensity;
        particleBlue = color3f.z * intensity;

        particleScale = rand.nextFloat() * 0.2F + 0.5F;
        setParticleTextureIndex((int) (Math.random() * 8.0D));

        particleMaxAge = UtilRandom.nextInt(minLife, maxLife);
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
}