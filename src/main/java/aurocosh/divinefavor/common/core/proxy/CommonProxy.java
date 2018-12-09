package aurocosh.divinefavor.common.core.proxy;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.common.ModBlocks;
import aurocosh.divinefavor.common.effect.common.ModPotionEffects;
import aurocosh.divinefavor.common.entity.ModEntities;
import aurocosh.divinefavor.common.favor_sources.common.ModFavorSources;
import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks;
import aurocosh.divinefavor.common.network.GuiHandler;
import aurocosh.divinefavor.common.network.common.MessageRegister;
import aurocosh.divinefavor.common.player_data.favor.FavorDataHandler;
import aurocosh.divinefavor.common.player_data.interaction_handler.InteractionDataHandler;
import aurocosh.divinefavor.common.receipes.ModRecipes;
import aurocosh.divinefavor.common.spell.common.ModSpells;
import aurocosh.divinefavor.common.spirit.ModSpirits;
import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        ModSpirits.preInit();
        ModFavors.preInit();
        ModSpells.preInit();
        ModMultiBlocks.preInit();
        ModFavorSources.preInit();

        ModPotionEffects.preInit();

        ModBlocks.preInit();
        ModItems.preInit();
        ModEntities.preInit();

        MessageRegister.init();

        FavorDataHandler.register();
        InteractionDataHandler.register();

        NetworkRegistry.INSTANCE.registerGuiHandler(DivineFavor.instance, new GuiHandler());

//        MinecraftForge.EVENT_BUS.preInit(new PlayerDataHandler.EventHandler());

        File test = e.getModConfigurationDirectory();

//        e.getSuggestedConfigurationFile().getParentFile().getParentFile();
    }

    public void init(FMLInitializationEvent e) {
        ModMultiBlocks.init();
        ModItems.init();
        ModBlocks.init();
        ModRecipes.init();
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
