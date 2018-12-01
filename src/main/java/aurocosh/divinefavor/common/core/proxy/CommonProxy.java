package aurocosh.divinefavor.common.core.proxy;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.base.ModBlocks;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.entity.ModEntities;
import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.item.base.ModItems;
import aurocosh.divinefavor.common.item.talisman.capability.TalismanDataHandler;
import aurocosh.divinefavor.common.network.GuiHandler;
import aurocosh.divinefavor.common.network.MessageRegister;
import aurocosh.divinefavor.common.receipes.ModRecipes;
import aurocosh.divinefavor.common.spell.base.ModSpells;
import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        //UtilAssets.Test();
        //UtilAssets.Test2();

        TalismanDataHandler.register();

        ModFavors.preInit();
        ModSpells.preInit();
        ModItems.preInit();
        ModBlocks.preInit();
        ModEntities.preInit();
        ModRecipes.init();

        MessageRegister.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(DivineFavor.instance, new GuiHandler());

        MinecraftForge.EVENT_BUS.register(new PlayerDataHandler.EventHandler());


    }

    public void init(FMLInitializationEvent e) {
        ModItems.init();
        ModBlocks.init();
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        throw new IllegalStateException("This should only be called from client side");
    }
    public EntityPlayer getClientPlayer() {
        throw new IllegalStateException("This should only be called from client side");
    }
}
