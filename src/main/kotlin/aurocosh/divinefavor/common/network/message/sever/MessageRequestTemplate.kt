package aurocosh.divinefavor.common.network.message.sever

import aurocosh.divinefavor.common.custom_data.global.TemplateData
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import aurocosh.divinefavor.common.network.message.client.MessageSendBlockTemplate
import net.minecraft.entity.player.EntityPlayerMP
import java.util.*

class MessageRequestTemplate : DivineServerMessage {
    var requestedMapUUIDS: List<UUID> = emptyList()

    constructor()

    constructor(list: List<UUID>) {
        requestedMapUUIDS = list
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val templateSavedData = serverPlayer.world[TemplateData]
        for (uuid in requestedMapUUIDS) {
            val template = templateSavedData.get(uuid)
            if (template != null)
                MessageSendBlockTemplate(uuid, template).sendTo(serverPlayer)
        }
    }
}
