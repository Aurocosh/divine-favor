package aurocosh.divinefavor.common.particles.base;

public interface IParticleRenderer<T extends ModParticle> {
    void renderParticles(Iterable<T> particles, float partialTicks);
}
