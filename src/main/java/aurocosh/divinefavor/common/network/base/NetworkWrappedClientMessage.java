package aurocosh.divinefavor.common.network.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public abstract class NetworkWrappedClientMessage extends NetworkClientMessage{
    private static SimpleNetworkWrapper networkWrapper;

    public NetworkWrappedClientMessage() { }

    public static void setNetworkWrapper(SimpleNetworkWrapper wrapper){
        networkWrapper = wrapper;
    }

    public void sendTo(EntityPlayer player){
        assert player instanceof EntityPlayerMP;
        networkWrapper.sendTo(this, (EntityPlayerMP) player);
    }
}
