package aurocosh.divinefavor.common.network.message.client.syncing

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.base.DivineClientMessage
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class MessageSyncSelectedTemplate : DivineClientMessage {
    lateinit var currentTemplate: UUID

    constructor()

    constructor(currentTemplate: UUID) {
        this.currentTemplate = currentTemplate
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer
        player.divinePlayerData.templateData.currentTemplate = currentTemplate
    }
}
