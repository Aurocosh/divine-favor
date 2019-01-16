package aurocosh.divinefavor.common.core.proxy;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.common.ModBlocks;
import aurocosh.divinefavor.common.entity.ModEntities;
import aurocosh.divinefavor.common.entity.common.ModGrudgeMobs;
import aurocosh.divinefavor.common.item.arrows.ModItemCursedArrows;
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.item.contract.ModContracts;
import aurocosh.divinefavor.common.item.grimoire.capability.GrimoireDataHandler;
import aurocosh.divinefavor.common.item.talismans.ModTalismans;
import aurocosh.divinefavor.common.item.wishing_stones.ModWishingStones;
import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks;
import aurocosh.divinefavor.common.network.GuiHandler;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import aurocosh.divinefavor.common.network.base.NetworkWrappedServerMessage;
import aurocosh.divinefavor.common.network.common.MessageRegister;
import aurocosh.divinefavor.common.network.common.NetworkHandler;
import aurocosh.divinefavor.common.player_data.escape_plan.EscapePlanDataHandler;
import aurocosh.divinefavor.common.player_data.gills.GillsDataHandler;
import aurocosh.divinefavor.common.player_data.molten_skin.MoltenSkinDataHandler;
import aurocosh.divinefavor.common.player_data.focused_fury.FocusedFuryDataHandler;
import aurocosh.divinefavor.common.player_data.grudge.GrudgeDataHandler;
import aurocosh.divinefavor.common.player_data.interaction_handler.InteractionDataHandler;
import aurocosh.divinefavor.common.player_data.pearl_crumbs.PearlCrumbsDataHandler;
import aurocosh.divinefavor.common.player_data.spell_count.SpellUsesDataHandler;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.receipes.ModRecipes;
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
        ModMultiBlocks.preInit();

        ModPotions.preInit();
        ModItemCursedArrows.preInit();
        ModTalismans.preInit();

        ModSpirits.preInit();
        ModContracts.preInit();
        ModCallingStones.preInit();
        ModWishingStones.preInit();


        ModBlocks.preInit();
        ModItems.preInit();
        ModEntities.preInit();

        ModGrudgeMobs.preInit();

        MessageRegister.init();
        NetworkWrappedClientMessage.setNetworkWrapper(NetworkHandler.INSTANCE);
        NetworkWrappedServerMessage.setNetworkWrapper(NetworkHandler.INSTANCE);

        EscapePlanDataHandler.register();
        FocusedFuryDataHandler.register();
        GillsDataHandler.register();
        GrimoireDataHandler.register();
        GrudgeDataHandler.register();
        InteractionDataHandler.register();
        MoltenSkinDataHandler.register();
        PearlCrumbsDataHandler.register();
        SpellUsesDataHandler.register();

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
