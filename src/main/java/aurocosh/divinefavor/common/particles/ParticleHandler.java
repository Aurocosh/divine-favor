package aurocosh.divinefavor.common.particles;

import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.particles.base.ModParticle;
import aurocosh.divinefavor.common.particles.generic.ParticleManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID, value = Side.CLIENT)
public final class ParticleHandler {
    private static final List<ParticleManager<? extends ModParticle>> particleManagers = new ArrayList<>();

    public static <T extends ModParticle> ParticleManager<T> register(ParticleManager<T> particleManager) {
        particleManagers.add(particleManager);
        return particleManager;
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