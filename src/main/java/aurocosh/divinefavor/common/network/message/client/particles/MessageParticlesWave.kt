package aurocosh.divinefavor.common.network.message.client.particles

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.network.base.WrappedClientMessage
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.math.Vec3d
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class MessageParticlesWave : WrappedClientMessage {
    var particleType: EnumParticleTypes = EnumParticleTypes.FLAME
    var position: Vec3d = Vec3d.ZERO

    constructor() {}

    constructor(particleType: EnumParticleTypes, position: Vec3d) {
        this.particleType = particleType
        this.position = position
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer

        val speedRange = 3.0
        for (i in 0 until ConfigSpells.heatWave.particleCount) {
            val xSpeed = UtilRandom.nextDouble(-speedRange, speedRange)
            val ySpeed = UtilRandom.nextDouble(-speedRange, speedRange)
            val zSpeed = UtilRandom.nextDouble(-speedRange, speedRange)

            player.world.spawnParticle(particleType, position.x, position.y, position.z, xSpeed, ySpeed, zSpeed)
        }
    }
}
