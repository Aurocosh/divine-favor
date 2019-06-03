package aurocosh.divinefavor.common.network.message.base

import aurocosh.autonetworklib.network.wrapped.WrappedClientMessage
import aurocosh.divinefavor.common.network.NetworkHandler
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper

abstract class DivineClientMessage : WrappedClientMessage() {
    override fun getWrapper(): SimpleNetworkWrapper {
        return NetworkHandler.wrapper
    }
}
