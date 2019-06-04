package aurocosh.divinefavor.common.particles

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.particles.base.ModParticle
import aurocosh.divinefavor.common.particles.generic.ParticleManager
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID, value = [Side.CLIENT])
object ParticleHandler {
    private val particleManagers = ArrayList<ParticleManager<out ModParticle>>()

    fun <T : ModParticle> register(particleManager: ParticleManager<T>): ParticleManager<T> {
        particleManagers.add(particleManager)
        return particleManager
    }

    @SubscribeEvent
    fun onRenderLast(event: RenderWorldLastEvent) {
        val mc = Minecraft.getMinecraft()
        mc.profiler.func_194340_a(ResourceNamer.debugSection("particleRendering"))
        val partialTicks = event.partialTicks
        for (particleManager in particleManagers)
            particleManager.renderParticles(partialTicks)
        mc.profiler.endSection()
    }

    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (event.phase == TickEvent.Phase.END)
            particleManagers.forEach { it.updateParticles() }
    }
}