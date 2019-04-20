package aurocosh.divinefavor.common.particles.particles;

import aurocosh.divinefavor.common.particles.base.ModParticle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EtherealParticle extends ModParticle {
    float reddustParticleScale;

    public EtherealParticle(World worldIn, Vec3d position, float scale, float red) {
        super(worldIn, position);
        motionX *= 0.10000000149011612D;
        motionY *= 0.10000000149011612D;
        motionZ *= 0.10000000149011612D;

        if (red == 0.0F)
            red = 8.0F;

        float f = (float) Math.random() * 0.4F + 0.6F;
        particleRed = ((float) (Math.random() * 0.20000000298023224D) + 0.8F) * red * f;
        particleGreen = 0;
        particleBlue = 0;
        particleScale *= 0.75F;
        particleScale *= scale;
        reddustParticleScale = particleScale;
        particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
        particleMaxAge = (int) ((float) particleMaxAge * scale);
    }

    /**
     * Renders the particle
     */
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        float f = ((float) particleAge + partialTicks) / (float) particleMaxAge * 32.0F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        particleScale = reddustParticleScale * f;
        super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
    }

    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (particleAge++ >= particleMaxAge)
            setExpired();

        setParticleTextureIndex(7 - particleAge * 8 / particleMaxAge);
        move(motionX, motionY, motionZ);

        if (posY == prevPosY) {
            motionX *= 1.1D;
            motionZ *= 1.1D;
        }

        motionX *= 0.9599999785423279D;
        motionY *= 0.9599999785423279D;
        motionZ *= 0.9599999785423279D;

        if (onGround) {
            motionX *= 0.699999988079071D;
            motionZ *= 0.699999988079071D;
        }
    }
}