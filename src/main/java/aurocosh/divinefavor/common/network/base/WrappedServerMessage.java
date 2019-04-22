package aurocosh.divinefavor.common.network.base;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public abstract class WrappedServerMessage extends NetworkServerMessage {
    private static SimpleNetworkWrapper networkWrapper;

    public WrappedServerMessage() { }

    public static void setNetworkWrapper(SimpleNetworkWrapper wrapper){
        networkWrapper = wrapper;
    }

    public void send(){
        networkWrapper.sendToServer(this);
    }
}
