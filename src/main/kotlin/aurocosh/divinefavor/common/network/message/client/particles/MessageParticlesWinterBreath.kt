package aurocosh.divinefavor.common.network.message.client.particles

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.network.base.WrappedClientMessage
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.math.Vec3d
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class MessageParticlesWinterBreath : WrappedClientMessage {
    var position: Vec3d = Vec3d.ZERO
    var direction: Vec3d = Vec3d.ZERO

    constructor() {}

    constructor(position: Vec3d, direction: Vec3d) {
        this.position = position
        this.direction = direction
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer

        val spread = 1.0
        for (i in 0 until ConfigSpells.winterBreath.particleCount) {
            val xSpeed = direction.x * 2 + UtilRandom.nextDouble(-spread, spread)
            val ySpeed = direction.y * 2 + UtilRandom.nextDouble(-spread, spread)
            val zSpeed = direction.z * 2 + UtilRandom.nextDouble(-spread, spread)

            player.world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, position.x, position.y, position.z, xSpeed, ySpeed, zSpeed)
        }
    }
}
