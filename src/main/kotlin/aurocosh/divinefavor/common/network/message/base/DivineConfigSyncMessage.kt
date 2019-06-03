package aurocosh.divinefavor.common.network.message.base

import aurocosh.autonetworklib.network.wrapped.ConfigSyncClientMessage
import aurocosh.divinefavor.common.network.NetworkHandler
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper

abstract class DivineConfigSyncMessage : ConfigSyncClientMessage() {
    override fun getWrapper(): SimpleNetworkWrapper {
        return NetworkHandler.wrapper
    }
}
