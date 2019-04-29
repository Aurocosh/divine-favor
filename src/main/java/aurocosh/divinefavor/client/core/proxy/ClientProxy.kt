package aurocosh.divinefavor.client.core.proxy

import aurocosh.divinefavor.client.core.handler.KeyBindings
import aurocosh.divinefavor.client.render.common.ModRendering
import aurocosh.divinefavor.common.core.proxy.CommonProxy
import aurocosh.divinefavor.common.particles.ModParticles
import com.google.common.util.concurrent.ListenableFuture
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

class ClientProxy : CommonProxy() {
    override fun preInit(event: FMLPreInitializationEvent) {
        super.preInit(event)

        ModParticles.preInit()
        ModRendering.preInit()

        //        MinecraftForge.EVENT_BUS.register(new HUDHandler());
        //        ClientRegistry.bindTileEntitySpecialRenderer(TileProgrammer.class, new RenderTileProgrammer());
    }

    override fun init(e: FMLInitializationEvent) {
        super.init(e)
        ModRendering.init()
        KeyBindings.init()
    }

    override fun addScheduledTaskClient(runnableToSchedule: Runnable): ListenableFuture<Any> {
        return Minecraft.getMinecraft().addScheduledTask(runnableToSchedule)
    }

    override val clientPlayer: EntityPlayer
        get() =  Minecraft.getMinecraft().player

    override val hasClientPlayer: Boolean
        get() = Minecraft.getMinecraft().player != null

}