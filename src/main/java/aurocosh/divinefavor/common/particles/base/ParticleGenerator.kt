package aurocosh.divinefavor.common.particles.base

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.particles.generic.ParticleManager
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.client.Minecraft
import net.minecraft.util.math.Vec3d

class ParticleGenerator<T : ModParticle>(protected val particleManager: ParticleManager<T>) {

    protected fun canCreateParticle(): Boolean {
        if (particleManager.isFull)
            return false

        val setting = mc.gameSettings.particleSetting
        return if (setting == 1 && UtilRandom.random.nextInt(3) != 0)
            false
        else
            setting != 2 || UtilRandom.random.nextInt(10) == 0
    }

    protected fun isCloseEnough(position: Vec3d): Boolean {
        val player = DivineFavor.proxy.clientPlayer
        return player.positionVector.squareDistanceTo(position) <= spawnRangeSq
    }

    fun createParticle(position: Vec3d, supplier: () -> T) {
        if (canCreateParticle() && isCloseEnough(position))
            particleManager.addParticle(supplier.invoke())
    }

    fun createParticle(supplier: () -> T) {
        if (canCreateParticle())
            particleManager.addParticle(supplier.invoke())
    }

    companion object {
        private val mc = Minecraft.getMinecraft()
        private val spawnRangeSq = ConfigGeneral.particleRadius * ConfigGeneral.particleRadius
    }
}
