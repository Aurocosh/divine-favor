package aurocosh.divinefavor.common.network.message.sever.syncing

import aurocosh.divinefavor.common.lib.EmptyConst.invalidUUID
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import net.minecraft.entity.player.EntityPlayerMP
import java.util.*

class MessageSyncTemplateServer : DivineServerMessage {
    var currentTemplate: UUID = invalidUUID()

    constructor()

    constructor(currentTemplate: UUID) {
        this.currentTemplate = currentTemplate
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        serverPlayer.divinePlayerData.templateData.currentTemplate = currentTemplate
    }
}
