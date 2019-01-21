package aurocosh.divinefavor.common.core.proxy;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.common.ModBlocks;
import aurocosh.divinefavor.common.custom_data.living.corrosion.ArmorCorrosionGillsDataHandler;
import aurocosh.divinefavor.common.custom_data.living.limp_leg.LimpLegDataHandler;
import aurocosh.divinefavor.common.custom_data.living.petrification.PetrificationDataHandler;
import aurocosh.divinefavor.common.custom_data.living.potion_status.PotionStatusGillsDataHandler;
import aurocosh.divinefavor.common.custom_data.living.wind_leash.WindLeashDataHandler;
import aurocosh.divinefavor.common.entity.ModEntities;
import aurocosh.divinefavor.common.entity.common.ModGrudgeMobs;
import aurocosh.divinefavor.common.item.talismans.arrow.common.ModArrowTalismans;
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.item.contract.ModContracts;
import aurocosh.divinefavor.common.item.grimoire.capability.GrimoireDataHandler;
import aurocosh.divinefavor.common.item.spell_bow.capability.SpellBowDataHandler;
import aurocosh.divinefavor.common.item.talismans.spell.common.ModSpellTalismans;
import aurocosh.divinefavor.common.item.wishing_stones.ModWishingStones;
import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks;
import aurocosh.divinefavor.common.network.GuiHandler;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import aurocosh.divinefavor.common.network.base.NetworkWrappedServerMessage;
import aurocosh.divinefavor.common.network.common.MessageRegister;
import aurocosh.divinefavor.common.network.common.NetworkHandler;
import aurocosh.divinefavor.common.custom_data.player.escape_plan.EscapePlanDataHandler;
import aurocosh.divinefavor.common.custom_data.player.gills.GillsDataHandler;
import aurocosh.divinefavor.common.custom_data.player.molten_skin.MoltenSkinDataHandler;
import aurocosh.divinefavor.common.custom_data.player.focused_fury.FocusedFuryDataHandler;
import aurocosh.divinefavor.common.custom_data.player.grudge.GrudgeDataHandler;
import aurocosh.divinefavor.common.custom_data.player.interaction_handler.InteractionDataHandler;
import aurocosh.divinefavor.common.custom_data.player.pearl_crumbs.PearlCrumbsDataHandler;
import aurocosh.divinefavor.common.custom_data.player.talisman_uses.TalismanUsesDataHandler;
import aurocosh.divinefavor.common.potions.common.ModCurses;
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
        ModCurses.preInit();
        ModArrowTalismans.preInit();
        ModItems.preInit();
        ModSpellTalismans.preInit();

        ModSpirits.preInit();
        ModContracts.preInit();
        ModCallingStones.preInit();
        ModWishingStones.preInit();

        ModBlocks.preInit();
        ModEntities.preInit();

        ModGrudgeMobs.preInit();

        MessageRegister.init();
        NetworkWrappedClientMessage.setNetworkWrapper(NetworkHandler.INSTANCE);
        NetworkWrappedServerMessage.setNetworkWrapper(NetworkHandler.INSTANCE);

        ArmorCorrosionGillsDataHandler.register();
        EscapePlanDataHandler.register();
        FocusedFuryDataHandler.register();
        GillsDataHandler.register();
        GrimoireDataHandler.register();
        GrudgeDataHandler.register();
        InteractionDataHandler.register();
        LimpLegDataHandler.register();
        MoltenSkinDataHandler.register();
        PearlCrumbsDataHandler.register();
        PetrificationDataHandler.register();
        PotionStatusGillsDataHandler.register();
        SpellBowDataHandler.register();
        TalismanUsesDataHandler.register();
        WindLeashDataHandler.register();

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
