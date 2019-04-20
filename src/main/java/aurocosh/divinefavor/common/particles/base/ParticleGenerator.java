package aurocosh.divinefavor.common.particles.base;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.common.ConfigGeneral;
import aurocosh.divinefavor.common.particles.generic.ParticleManager;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

import java.util.function.Supplier;

public class ParticleGenerator<T extends ModParticle> {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final int spawnRangeSq = ConfigGeneral.particleRadius * ConfigGeneral.particleRadius;

    protected final ParticleManager<T> particleManager;

    public ParticleGenerator(ParticleManager<T> particleManager) {
        this.particleManager = particleManager;
    }

    protected boolean canCreateParticle() {
        if (particleManager.isFull())
            return false;

        int setting = mc.gameSettings.particleSetting;
        if (setting == 1 && UtilRandom.random.nextInt(3) != 0)
            return false;
        else
            return setting != 2 || UtilRandom.random.nextInt(10) == 0;
    }

    protected boolean isCloseEnough(Vec3d position) {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        return player.getPositionVector().squareDistanceTo(position) <= spawnRangeSq;
    }

    public void createParticle(Vec3d position, Supplier<T> supplier) {
        if (canCreateParticle() && isCloseEnough(position))
            particleManager.addParticle(supplier.get());
    }

    public void createParticle(Supplier<T> supplier) {
        if (canCreateParticle())
            particleManager.addParticle(supplier.get());
    }
}
