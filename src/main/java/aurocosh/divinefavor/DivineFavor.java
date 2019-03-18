package aurocosh.divinefavor;

import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.core.creative_tabs.*;
import aurocosh.divinefavor.common.core.proxy.CommonProxy;
import aurocosh.divinefavor.common.log.ModLogger;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_NAME, version = ConstMisc.VERSION, dependencies = "required-after:patchouli;")
public class DivineFavor {
    @Mod.Instance(ConstMisc.MOD_ID)
    public static DivineFavor instance;

    public static ModLogger logger;
    public static ModContainer container;
    public static DivineFavorCreativeTab TAB_MAIN = new DivineFavorCreativeTab();
    public static DivineFavorCreativeTabGems TAB_GEMS = new DivineFavorCreativeTabGems();
    public static final DivineFavorCreativeTabArrowTalismans TAB_ARROW_TALISMANS = new DivineFavorCreativeTabArrowTalismans();
    public static DivineFavorCreativeTabSpellTalismans TAB_SPELL_TALISMANS = new DivineFavorCreativeTabSpellTalismans();

    @SidedProxy(serverSide = ConstMisc.PROXY_COMMON, clientSide = ConstMisc.PROXY_CLIENT)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = new ModLogger(event.getModLog());
        container = Loader.instance().getModObjectList().inverse().get(instance);
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void serverStartingEvent(FMLServerStartingEvent event) {

    }
}