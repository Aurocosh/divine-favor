package aurocosh.divinefavor.common.network.base;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public abstract class NetworkWrappedServerMessage extends NetworkServerMessage {
    private static SimpleNetworkWrapper networkWrapper;

    public NetworkWrappedServerMessage() { }

    public static void setNetworkWrapper(SimpleNetworkWrapper wrapper){
        networkWrapper = wrapper;
    }

    public void send(){
        networkWrapper.sendToServer(this);
    }
}
