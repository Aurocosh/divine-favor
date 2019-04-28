package aurocosh.divinefavor.common.particles.base

interface IParticleRenderer<T : ModParticle> {
    fun renderParticles(particles: Iterable<T>, partialTicks: Float)
}
