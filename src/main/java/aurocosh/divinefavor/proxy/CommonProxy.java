package aurocosh.divinefavor.proxy;

import aurocosh.divinefavor.ModBlocks;
import aurocosh.divinefavor.block.BlockFastFurnace;
import aurocosh.divinefavor.block.BlockIronMedium;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockFastFurnace());
        event.getRegistry().register(new BlockIronMedium());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.blockFastFurnace).setRegistryName(ModBlocks.FAST_FURNACE));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockIronMedium).setRegistryName(ModBlocks.IRON_MEDIUM));
    }
}
