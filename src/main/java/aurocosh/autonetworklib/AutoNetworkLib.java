//package aurocosh.autonetworklib;
//
//import aurocosh.autonetworklib.proxy.CommonProxy;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.common.Mod.EventHandler;
//import net.minecraftforge.fml.common.Mod.Instance;
//import net.minecraftforge.fml.common.SidedProxy;
//import net.minecraftforge.fml.common.event.FMLInitializationEvent;
//import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
//import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//@Mod(modid = AutoNetworkLib.MOD_ID, name = AutoNetworkLib.MOD_NAME, dependencies = AutoNetworkLib.DEPS)
//public class AutoNetworkLib {
//    // Mod Constants
//    public final static String MOD_ID = "autonetworklib";
//    public final static String MOD_NAME = "AutoNetworkLib";
//    public static final String DEPS = "required-after:forge@[14.23.5.2768,)";
//
//    // Proxy Constants
//    public final static String PROXY_COMMON = "aurocosh.autonetworklib.proxy.CommonProxy";
//    public final static String PROXY_CLIENT = "aurocosh.autonetworklib.proxy.ClientProxy";
//
//	@Instance(AutoNetworkLib.MOD_ID)
//	public static AutoNetworkLib instance;
//
//	@SidedProxy(serverSide = AutoNetworkLib.PROXY_COMMON, clientSide = AutoNetworkLib.PROXY_CLIENT)
//	public static CommonProxy proxy;
//    public static Logger logger;
//
//	@EventHandler
//	public void preInit(FMLPreInitializationEvent event) {
//        logger = LogManager.getLogger();
//		proxy.preInit(event);
//	}
//
//	@EventHandler
//	public void init(FMLInitializationEvent event) {
//		proxy.init(event);
//	}
//
//	@EventHandler
//	public void postInit(FMLPostInitializationEvent event) {
//		proxy.postInit(event);
//	}
//}
