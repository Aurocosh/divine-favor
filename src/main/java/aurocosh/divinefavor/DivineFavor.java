package aurocosh.divinefavor;

import aurocosh.divinefavor.proxy.CommonProxy;
import mcjty.theoneprobe.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = DivineFavor.MODID, name = DivineFavor.MODNAME, version = DivineFavor.MODVERSION, dependencies = "required-after:forge@[14.23.5.2768,)", useMetadata = true)
public class DivineFavor {

    public static final String MODID = "divinefavor";
    public static final String MODNAME = "Divine Favor";
    public static final String MODVERSION= "0.0.1";

    @SidedProxy(clientSide = "aurocosh.divinefavor.proxy.ClientProxy", serverSide = "aurocosh.divinefavor.proxy.ServerProxy")
    public static CommonProxy proxy;

    public static CreativeTabs tabDivineFavor = new CreativeTabs("Divine Favor") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModBlocks.blockFastFurnace);
        }
    };

    @Mod.Instance
    public static DivineFavor instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}