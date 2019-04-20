package aurocosh.divinefavor.common.particles.generic;

import aurocosh.divinefavor.common.particles.base.IParticleRenderer;
import aurocosh.divinefavor.common.particles.base.ModParticle;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@SideOnly(Side.CLIENT)
public class ParticleManager<T extends ModParticle> {

    private final List<T> particles;
    private final IParticleRenderer<T> renderer;
    private final Supplier<T> supplier;

    public ParticleManager(IParticleRenderer<T> renderer, Supplier<T> supplier) {
        this.particles = new ArrayList<>();
        this.renderer = renderer;
        this.supplier = supplier;
    }

    public void addParticle(T particle) {
        if (particle != null)
            particles.add(particle);
    }

    public T getParticle() {
        return supplier.get();
    }

    public void updateParticles() {
        for (T particle : particles)
            particle.onUpdate();
        UtilList.filterList(particles, ModParticle::isDead);
    }

    public void renderParticles(float partialTicks) {
        renderer.renderParticles(particles, partialTicks);
    }

    public void clear() {
        particles.clear();
    }
}