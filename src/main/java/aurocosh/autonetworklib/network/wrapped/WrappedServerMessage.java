package aurocosh.autonetworklib.network.wrapped;

import aurocosh.autonetworklib.network.IWrapperProvider;
import aurocosh.autonetworklib.network.message.NetworkServerMessage;

public abstract class WrappedServerMessage extends NetworkServerMessage implements IWrapperProvider {
    public void send() {
        getWrapper().sendToServer(this);
    }
}
