package aurocosh.divinefavor.common.network.message.client.syncing

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.network.base.WrappedClientMessage
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class MessageSyncFlyingCapability : WrappedClientMessage {
    var canFly: Boolean = false

    constructor() {}

    constructor(canFly: Boolean) {
        this.canFly = canFly
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer
        player.capabilities.allowFlying = canFly
        player.capabilities.isFlying = canFly
    }
}
