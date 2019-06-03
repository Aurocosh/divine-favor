package aurocosh.divinefavor.common.network.message.client.syncing

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.autonetworklib.network.base.WrappedClientMessage
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class MessageSyncEvilEye : WrappedClientMessage {
    var severity: Int = 0

    constructor() {}

    constructor(severity: Int) {
        this.severity = severity
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer
        player.divinePlayerData.evilEyeData.severity = severity
    }
}
