package aurocosh.divinefavor.common.particles.particles

import aurocosh.divinefavor.common.particles.base.ModParticle
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import javax.vecmath.Color3f

@SideOnly(Side.CLIENT)
class ImmobileParticle(worldIn: World, position: Vec3d, color3f: Color3f, maxAge: Int) : ModParticle(worldIn, position) {
    init {
        posX = position.x
        posY = position.y
        posZ = position.z

        prevPosX = posX
        prevPosY = posY
        prevPosZ = posZ

        val intensity = rand.nextFloat() * 0.6f + 0.4f
        particleRed = color3f.x * intensity
        particleGreen = color3f.y * intensity
        particleBlue = color3f.z * intensity

        particleScale = rand.nextFloat() * 0.2f + 0.5f
        setParticleTextureIndex((Math.random() * 8.0).toInt())

        particleMaxAge = maxAge
    }

    override fun onUpdate() {
        if (particleAge++ >= particleMaxAge)
            setExpired()
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