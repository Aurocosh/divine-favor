package aurocosh.divinefavor.common.core.proxy;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.base.ModBlocks;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.entity.ModEntities;
import aurocosh.divinefavor.common.item.base.ModItems;
import aurocosh.divinefavor.common.network.GuiHandler;
import aurocosh.divinefavor.common.network.MessageRegister;
import aurocosh.divinefavor.common.spell.base.ModSpells;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        //ConfigHandler.init(event.getSuggestedConfigurationFile());

        ModItems.preInit();
        ModBlocks.preInit();
        ModEntities.init();
        ModSpells.init();
        //ModCraftingRecipes.init();

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

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        //event.getRegistry().register(new BlockFastFurnace());
        //event.getRegistry().register(new BlockIronMedium());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        //event.getRegistry().register(new ItemBlock(ModBlocks.blockFastFurnace).setRegistryName(ModBlocks.FAST_FURNACE));
        //event.getRegistry().register(new ItemBlock(ModBlocks.blockIronMedium).setRegistryName(ModBlocks.IRON_MEDIUM));
    }

    public EntityPlayer getClientPlayer() {
        return null;
    }

    public void onLevelUp(int level) {
        // proxy override
    }

    public void savePersistency() {
        // proxy override
    }
}
