package aurocosh.divinefavor.common.particles.particles

import aurocosh.divinefavor.common.particles.base.ModParticle
import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.entity.Entity
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

import javax.vecmath.Color3f

@SideOnly(Side.CLIENT)
class OreHighlightParticle(worldIn: World, position: Vec3d, color3f: Color3f) : ModParticle(worldIn, position) {
    private val portalParticleScale: Float

    init {
        posX = position.x
        posY = position.y
        posZ = position.z

        canCollide = false

        val intensity = rand.nextFloat() * 0.6f + 0.4f
        particleRed = color3f.x * intensity
        particleGreen = color3f.y * intensity
        particleBlue = color3f.z * intensity

        particleScale = rand.nextFloat() * 0.2f + 0.5f
        portalParticleScale = particleScale
        particleMaxAge = (Math.random() * 10.0).toInt() + 40
        setParticleTextureIndex(98)
    }

    /**
     * Renders the particle
     */
    override fun renderParticle(buffer: BufferBuilder, entityIn: Entity, partialTicks: Float, rotationX: Float, rotationZ: Float, rotationYZ: Float, rotationXY: Float, rotationXZ: Float) {
        var f = (particleAge.toFloat() + partialTicks) / particleMaxAge.toFloat()
        f = 1.0f - f
        f *= f
        f = 1.0f - f
        particleScale = portalParticleScale * f
        super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ)
    }

    override fun getBrightnessForRender(partialTick: Float): Int {
        val i = super.getBrightnessForRender(partialTick)
        var f = particleAge.toFloat() / particleMaxAge.toFloat()
        f *= f
        f *= f
        val j = i and 255
        var k = i shr 16 and 255
        k += (f * 15.0f * 16.0f).toInt()

        if (k > 240) {
            k = 240
        }

        return j or (k shl 16)
    }
}