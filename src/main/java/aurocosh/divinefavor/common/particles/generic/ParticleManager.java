package aurocosh.divinefavor.common.particles.generic;

import aurocosh.divinefavor.common.particles.base.IModParticleFactory;
import aurocosh.divinefavor.common.particles.base.IParticleRenderer;
import aurocosh.divinefavor.common.particles.base.ModParticle;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public final class ParticleManager<T extends ModParticle> {

    private final List<T> particles;
    private final IParticleRenderer<T> particleRenderer;
    private final IModParticleFactory<T> particleFactory;

    public ParticleManager(IParticleRenderer<T> particleRenderer, IModParticleFactory<T> particleFactory) {
        this.particleFactory = particleFactory;
        this.particles = new ArrayList<>();
        this.particleRenderer = particleRenderer;
    }

    public void createParticle(World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... vars) {
        T particle = particleFactory.createParticle(world, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, vars);
        if (particle != null)
            particles.add(particle);
    }

    public void updateParticles() {
        for (T particle : particles)
            particle.onUpdate();
        UtilList.filterList(particles, ModParticle::isDead);
    }

    public void renderParticles(float partialTicks) {
        particleRenderer.renderParticles(particles, partialTicks);
    }

    public void clear() {
        particles.clear();
    }
}