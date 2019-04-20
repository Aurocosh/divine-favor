package aurocosh.divinefavor.client.core.proxy;

import aurocosh.divinefavor.client.core.handler.KeyBindings;
import aurocosh.divinefavor.client.render.common.ModRendering;
import aurocosh.divinefavor.common.core.proxy.CommonProxy;
import aurocosh.divinefavor.common.particles.ModParticles;
import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        ModParticles.preInit();
        ModRendering.preInit();

//        MinecraftForge.EVENT_BUS.register(new HUDHandler());
//        ClientRegistry.bindTileEntitySpecialRenderer(TileProgrammer.class, new RenderTileProgrammer());
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        ModRendering.init();
        KeyBindings.init();
    }

    @Override
    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        return Minecraft.getMinecraft().addScheduledTask(runnableToSchedule);
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }
}