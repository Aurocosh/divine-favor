package aurocosh.divinefavor;

import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.core.proxy.CommonProxy;
import aurocosh.divinefavor.common.lib.LibMisc;
import aurocosh.divinefavor.common.log.ModLogger;
import aurocosh.divinefavor.common.registry.ConfigRegistry;
import aurocosh.divinefavor.common.registry.EventRegistry;
import aurocosh.divinefavor.common.registry.content.ItemPotionContent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.File;
import java.util.ArrayList;

@Mod(modid = LibMisc.MOD_ID, name = LibMisc.MOD_NAME, version = LibMisc.VERSION, guiFactory = LibMisc.GUI_FACTORY, dependencies = LibMisc.DEPENDENCIES)
public class DivineFavor {
    @Mod.Instance(LibMisc.MOD_ID)
    public static DivineFavor instance;
    public static ModLogger logger;
    public EventRegistry events;
    private ArrayList<IContent> content;
    //public static SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(LibMisc.MOD_ID);
    //PacketRegistry.register(network);

    DivineFavorCreativeTab tab = DivineFavorCreativeTab.INSTANCE;

    @SidedProxy(serverSide = LibMisc.PROXY_COMMON, clientSide = LibMisc.PROXY_CLIENT)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = new ModLogger(event.getModLog());
        ConfigRegistry.oreConfig = new Configuration(new File(event.getModConfigurationDirectory(), "cyclic_ores.cfg"));
        ConfigRegistry.init(new Configuration(event.getSuggestedConfigurationFile()));
        ConfigRegistry.register(logger);



        events = new EventRegistry();
        events.registerCoreEvents();


        //ModPotionEffects.register();

        proxy.preInit(event);
        content = new ArrayList<IContent>();
        content.add(new ItemPotionContent());
        for (IContent cont : content) {
            ConfigRegistry.register(cont);
        }
        ConfigRegistry.syncAllConfig();
        for (IContent cont : content) {
            if (cont.enabled()) {
                cont.register();
            }
        }

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        events.registerAll(); //important: register events AFTER modules onInit, since modules add events in this phase.
    }

    @Mod.EventHandler
    public void serverStartingEvent(FMLServerStartingEvent event) {
//		event.registerServerCommand(new CommandDownloadLatest());


    }


}