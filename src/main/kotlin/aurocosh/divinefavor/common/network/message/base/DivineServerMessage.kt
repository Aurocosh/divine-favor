package aurocosh.divinefavor.common.network.message.base

import aurocosh.autonetworklib.network.wrapped.WrappedServerMessage
import aurocosh.divinefavor.common.network.NetworkHandler
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper

abstract class DivineServerMessage : WrappedServerMessage() {
    override fun getWrapper(): SimpleNetworkWrapper {
        return NetworkHandler.wrapper
    }
}
