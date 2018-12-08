package aurocosh.divinefavor.client.core.proxy;

import aurocosh.divinefavor.client.core.handler.ClientTickHandler;
import aurocosh.divinefavor.client.core.handler.HUDHandler;
import aurocosh.divinefavor.client.core.handler.KeybindHandler;
import aurocosh.divinefavor.client.render.entity.ModRendering;
import aurocosh.divinefavor.common.core.proxy.CommonProxy;
import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        ModRendering.preInit();

        MinecraftForge.EVENT_BUS.register(new HUDHandler());
        MinecraftForge.EVENT_BUS.register(new ClientTickHandler());

        //ClientRegistry.bindTileEntitySpecialRenderer(TileProgrammer.class, new RenderTileProgrammer());
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        ModRendering.init();
        KeybindHandler.init();
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