//package aurocosh.autonetworklib.proxy;
//
//import aurocosh.autonetworklib.network.serialization.serializer_provider.BufSerializerProvider;
//import com.google.common.util.concurrent.ListenableFuture;
//import net.minecraftforge.fml.common.event.FMLInitializationEvent;
//import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
//import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
//
//public class CommonProxy {
//    public void preInit(FMLPreInitializationEvent event) {
//        BufSerializerProvider.preInit();
//    }
//
//    public void init(FMLInitializationEvent event) {
//    }
//
//    public void postInit(FMLPostInitializationEvent event) {
//    }
//
//    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
//        throw new IllegalStateException("This should only be called from client side");
//    }
//}
