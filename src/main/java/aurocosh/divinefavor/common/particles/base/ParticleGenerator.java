package aurocosh.divinefavor.common.particles.base;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.particles.generic.ParticleManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class ParticleGenerator<T extends ModParticle> {
    private static final int spawnRange = 32;
    private static final int spawnRangeSq = spawnRange * spawnRange;

    protected final ParticleManager<T> particleManager;

    public ParticleGenerator(ParticleManager<T> particleManager) {
        this.particleManager = particleManager;
    }

    protected boolean canCreateParticle(Vec3d position) {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        return player.getPositionVector().squareDistanceTo(position) <= spawnRangeSq;
    }
}
