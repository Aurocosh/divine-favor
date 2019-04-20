package aurocosh.divinefavor.common.particles.generic;

import aurocosh.divinefavor.common.particles.base.IParticleRenderer;
import aurocosh.divinefavor.common.particles.base.ModParticle;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class ParticleManager<T extends ModParticle> {
    private final int limit;
    private final List<T> particles;
    private final IParticleRenderer<T> renderer;

    public ParticleManager(int limit, IParticleRenderer<T> renderer) {
        this.limit = limit;
        this.particles = new ArrayList<>();
        this.renderer = renderer;
    }

    public void addParticle(T particle) {
        if (!isFull() && particle != null)
            particles.add(particle);
    }

    public boolean isFull() {
        return particles.size() >= limit;
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