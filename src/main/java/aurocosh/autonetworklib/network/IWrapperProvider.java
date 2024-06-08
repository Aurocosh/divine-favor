package aurocosh.autonetworklib.network;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public interface IWrapperProvider {
    SimpleNetworkWrapper getWrapper();
}
