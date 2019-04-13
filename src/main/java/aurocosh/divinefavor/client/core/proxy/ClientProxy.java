package aurocosh.divinefavor.client.core.proxy;

import aurocosh.divinefavor.client.core.handler.KeyBindings;
import aurocosh.divinefavor.client.render.common.ModRendering;
import aurocosh.divinefavor.common.core.proxy.CommonProxy;
import aurocosh.divinefavor.common.lib.interfaces.IBlockStateItemModelDefinition;
import aurocosh.divinefavor.common.lib.interfaces.ICustomMeshCallback;
import aurocosh.divinefavor.common.lib.interfaces.IMultipleItemModelDefinition;
import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.HashMap;
import java.util.Map;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

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