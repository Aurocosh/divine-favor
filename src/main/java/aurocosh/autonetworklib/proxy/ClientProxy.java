//
//package aurocosh.autonetworklib.proxy;
//
//import com.google.common.util.concurrent.ListenableFuture;
//import net.minecraft.client.Minecraft;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//@SideOnly(Side.CLIENT)
//public class ClientProxy extends CommonProxy {
//    @Override
//    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
//        return Minecraft.getMinecraft().addScheduledTask(runnableToSchedule);
//    }
//}
