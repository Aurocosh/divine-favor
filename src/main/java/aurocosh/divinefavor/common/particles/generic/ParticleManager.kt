package aurocosh.divinefavor.common.particles.generic

import aurocosh.divinefavor.common.particles.base.IParticleRenderer
import aurocosh.divinefavor.common.particles.base.ModParticle
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

@SideOnly(Side.CLIENT)
class ParticleManager<T : ModParticle>(private val limit: Int, private val renderer: IParticleRenderer<T>) {
    private val particles: MutableList<T>

    val isFull: Boolean
        get() = particles.size >= limit

    init {
        this.particles = ArrayList()
    }

    fun addParticle(particle: T?) {
        if (!isFull && particle != null)
            particles.add(particle)
    }

    fun updateParticles() {
        for (particle in particles)
            particle.onUpdate()
        particles.removeIf { it.isDead }
    }

    fun renderParticles(partialTicks: Float) {
        renderer.renderParticles(particles, partialTicks)
    }

    fun clear() {
        particles.clear()
    }
}