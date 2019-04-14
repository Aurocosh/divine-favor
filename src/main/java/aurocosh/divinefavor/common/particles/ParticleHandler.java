package aurocosh.divinefavor.common.particles;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.particles.generic.ParticleManager;
import aurocosh.divinefavor.common.particles.types.ParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public final class ParticleHandler {
    private static final int spawnRange = 32;
    private static final int spawnRangeSq = spawnRange * spawnRange;

    private static final List<ParticleType> particleTypes = new ArrayList<>();
    private static final List<ParticleManager> particleManagers = new ArrayList<>();

    public static void createParticle(ParticleType particleType, World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... vars) {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        if (player.getDistanceSq(xCoord, yCoord, zCoord) > spawnRangeSq)
            return;
        if (particleType.id >= particleTypes.size())
            return;

        ParticleManager particleManager = particleManagers.get(particleType.id);
        particleManager.createParticle(world, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, vars);
    }

    public static ParticleType register(ParticleManager particleManager) {
        int id = particleTypes.size();
        ParticleType particleType = new ParticleType(id);
        particleTypes.add(particleType);
        particleManagers.add(particleManager);
        return particleType;
    }

    @SubscribeEvent
    public static void onRenderLast(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.profiler.func_194340_a(ResourceNamer.debugSection("particleRendering"));
        float partialTicks = event.getPartialTicks();
        for (ParticleManager particleManager : particleManagers)
            particleManager.renderParticles(partialTicks);
        mc.profiler.endSection();
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END)
            particleManagers.forEach(ParticleManager::updateParticles);
    }
}