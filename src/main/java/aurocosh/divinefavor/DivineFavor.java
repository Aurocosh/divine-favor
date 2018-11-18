package aurocosh.divinefavor;

import aurocosh.divinefavor.common.block.base.ModBlocks;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.lib.LibMisc;
import aurocosh.divinefavor.common.core.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = LibMisc.MOD_ID, name = LibMisc.MOD_NAME, version = LibMisc.VERSION, guiFactory = LibMisc.GUI_FACTORY, dependencies = LibMisc.DEPENDENCIES)
public class DivineFavor {
    @Mod.Instance(LibMisc.MOD_ID)
    public static DivineFavor instance;

    DivineFavorCreativeTab tab = DivineFavorCreativeTab.INSTANCE;

    @SidedProxy(serverSide = LibMisc.PROXY_COMMON, clientSide = LibMisc.PROXY_CLIENT)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void serverStartingEvent(FMLServerStartingEvent event) {
//		event.registerServerCommand(new CommandDownloadLatest());
    }
}