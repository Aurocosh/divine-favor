package aurocosh.divinefavor.client.core.proxy;

import aurocosh.divinefavor.client.core.handler.ClientTickHandler;
import aurocosh.divinefavor.client.core.handler.HUDHandler;
import aurocosh.divinefavor.client.core.handler.KeybindHandler;
import aurocosh.divinefavor.common.core.handlers.PersistencyHandler;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.core.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.awt.*;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        KeybindHandler.init();

//		if(ConfigHandler.versionCheckEnabled)
//			new VersionChecker().init();

        MinecraftForge.EVENT_BUS.register(new HUDHandler());
        MinecraftForge.EVENT_BUS.register(new ClientTickHandler());

        //ClientRegistry.bindTileEntitySpecialRenderer(TileProgrammer.class, new RenderTileProgrammer());

        //RenderingRegistry.registerEntityRenderingHandler(EntitySpellCircle.class, manager -> new RenderSpellCircle(manager));
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }

    @Override
    public void onLevelUp(int level) {
        HUDHandler.levelUp(level);
    }

    @Override
    public void savePersistency() {
        PersistencyHandler.save(PlayerDataHandler.get(getClientPlayer()).level);
    }
}