package aurocosh.divinefavor.common.particles.base;

import javax.annotation.Nullable;

public interface IModParticleFactory<T extends ModParticle> {
    T createParticle();
}
