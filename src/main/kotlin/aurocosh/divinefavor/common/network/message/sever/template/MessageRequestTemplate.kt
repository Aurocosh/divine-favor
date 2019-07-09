package aurocosh.divinefavor.common.network.message.sever.template

import aurocosh.divinefavor.common.custom_data.global.TemplateData
import aurocosh.divinefavor.common.lib.EmptyConst.invalidUUID
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.network.TemplateNetHandlers
import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import net.minecraft.entity.player.EntityPlayerMP
import java.util.*

class MessageRequestTemplate : DivineServerMessage {
    var uuid: UUID = invalidUUID()
    var requestedMapUUIDS: List<UUID> = emptyList()

    constructor()

    constructor(list: List<UUID>) {
        requestedMapUUIDS = list
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val templateSavedData = serverPlayer.world[TemplateData]
        for (uuid in requestedMapUUIDS) {
            val template = templateSavedData[uuid]
            if (template != null)
                TemplateNetHandlers.clientHandler.send(serverPlayer, template)
        }
    }
}
