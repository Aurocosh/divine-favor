package aurocosh.divinefavor.common.particles.particles

import aurocosh.divinefavor.common.particles.base.ModParticle
import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.entity.Entity
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
class EtherealParticle(worldIn: World, position: Vec3d, scale: Float, red: Float) : ModParticle(worldIn, position) {
    internal var reddustParticleScale: Float = 0.toFloat()

    init {
        var red = red
        motionX *= 0.10000000149011612
        motionY *= 0.10000000149011612
        motionZ *= 0.10000000149011612

        if (red == 0.0f)
            red = 8.0f

        val f = Math.random().toFloat() * 0.4f + 0.6f
        particleRed = ((Math.random() * 0.20000000298023224).toFloat() + 0.8f) * red * f
        particleGreen = 0f
        particleBlue = 0f
        particleScale *= 0.75f
        particleScale *= scale
        reddustParticleScale = particleScale
        particleMaxAge = (8.0 / (Math.random() * 0.8 + 0.2)).toInt()
        particleMaxAge = (particleMaxAge.toFloat() * scale).toInt()
    }

    /**
     * Renders the particle
     */
    override fun renderParticle(buffer: BufferBuilder, entityIn: Entity?, partialTicks: Float, rotationX: Float, rotationZ: Float, rotationYZ: Float, rotationXY: Float, rotationXZ: Float) {
        var f = (particleAge.toFloat() + partialTicks) / particleMaxAge.toFloat() * 32.0f
        f = MathHelper.clamp(f, 0.0f, 1.0f)
        particleScale = reddustParticleScale * f
        super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ)
    }

    override fun onUpdate() {
        prevPosX = posX
        prevPosY = posY
        prevPosZ = posZ

        if (particleAge++ >= particleMaxAge)
            setExpired()

        setParticleTextureIndex(7 - particleAge * 8 / particleMaxAge)
        move(motionX, motionY, motionZ)

        if (posY == prevPosY) {
            motionX *= 1.1
            motionZ *= 1.1
        }

        motionX *= 0.9599999785423279
        motionY *= 0.9599999785423279
        motionZ *= 0.9599999785423279

        if (onGround) {
            motionX *= 0.699999988079071
            motionZ *= 0.699999988079071
        }
    }
}