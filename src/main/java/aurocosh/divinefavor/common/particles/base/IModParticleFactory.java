package aurocosh.divinefavor.common.particles.base;

public interface IModParticleFactory<T extends ModParticle> {
    T createParticle();
}
