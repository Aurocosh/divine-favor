package aurocosh.divinefavor.common.particles.base;

import java.util.List;

public interface IParticleRenderer<T extends ModParticle> {
    void renderParticles(List<T> particles, float partialTicks);
}
