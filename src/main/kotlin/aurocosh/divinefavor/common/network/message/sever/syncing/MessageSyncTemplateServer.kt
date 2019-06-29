package aurocosh.divinefavor.common.network.message.sever.syncing

import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import net.minecraft.entity.player.EntityPlayerMP
import java.util.*

class MessageSyncTemplateServer : DivineServerMessage {
    lateinit var currentTemplate: UUID

    constructor()

    constructor(currentTemplate: UUID) {
        this.currentTemplate = currentTemplate
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        serverPlayer.divinePlayerData.templateData.currentTemplate = currentTemplate
    }
}
