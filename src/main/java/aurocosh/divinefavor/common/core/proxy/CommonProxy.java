package aurocosh.divinefavor.common.core.proxy;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.common.ModBlocks;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.custom_data.living.capability.LivingDataDataHandler;
import aurocosh.divinefavor.common.custom_data.player.capability.PlayerDataDataHandler;
import aurocosh.divinefavor.common.custom_data.world.capability.WorldDataDataHandler;
import aurocosh.divinefavor.common.entity.ModEntities;
import aurocosh.divinefavor.common.item.bathing_blend.ModBathingBlends;
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.item.contract.ModContracts;
import aurocosh.divinefavor.common.item.soul_shards.ModSoulShards;
import aurocosh.divinefavor.common.item.talisman_container.grimoire.capability.GrimoireDataHandler;
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.capability.SpellBowDataHandler;
import aurocosh.divinefavor.common.item.talismans.arrow.common.ModArrowTalismans;
import aurocosh.divinefavor.common.item.talismans.spell.common.ModSpellTalismans;
import aurocosh.divinefavor.common.item.wishing_stones.ModWishingStones;
import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks;
import aurocosh.divinefavor.common.network.GuiHandler;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import aurocosh.divinefavor.common.network.base.NetworkWrappedServerMessage;
import aurocosh.divinefavor.common.network.common.MessageRegister;
import aurocosh.divinefavor.common.network.common.NetworkHandler;
import aurocosh.divinefavor.common.potions.common.ModBlendEffects;
import aurocosh.divinefavor.common.potions.common.ModBlessings;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.receipes.ModRecipes;
import aurocosh.divinefavor.common.receipes.RecipeLoader;
import aurocosh.divinefavor.common.spirit.ModSpirits;
import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        ModSpirits.preInit();
        ModMultiBlocks.preInit();

        ModPotions.preInit();
        ModCurses.preInit();
        ModBlendEffects.preInit();
        ModBlessings.preInit();
        ModArrowTalismans.preInit();
        ModItems.preInit();
        ModSoulShards.preInit();
        ModSpellTalismans.preInit();
        ModBathingBlends.preInit();

        ModContracts.preInit();
        ModCallingStones.preInit();
        ModWishingStones.preInit();

        ModBlocks.preInit();
        ModEntities.preInit();

        MessageRegister.init();
        NetworkWrappedClientMessage.setNetworkWrapper(NetworkHandler.INSTANCE);
        NetworkWrappedServerMessage.setNetworkWrapper(NetworkHandler.INSTANCE);

        LivingDataDataHandler.register();
        PlayerDataDataHandler.register();
        WorldDataDataHandler.register();

        GrimoireDataHandler.register();
        SpellBowDataHandler.register();

        NetworkRegistry.INSTANCE.registerGuiHandler(DivineFavor.instance, new GuiHandler());

//        MinecraftForge.EVENT_BUS.preInit(new PlayerDataHandler.LoginDataSyncer());

        File test = e.getModConfigurationDirectory();

//        TalismanPagesGenerator.generate();

//        e.getSuggestedConfigurationFile().getParentFile().getParentFile();
    }

    public void init(FMLInitializationEvent e) {
        ModMultiBlocks.init();
        ModItems.init();
        ModBlocks.init();
        ModRecipes.init();
        RecipeLoader.init();
    }

    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(ConstMisc.MOD_ID))
            ConfigManager.sync(ConstMisc.MOD_ID, Config.Type.INSTANCE);
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
