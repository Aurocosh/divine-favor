package aurocosh.divinefavor.common.network.message.sever

import aurocosh.divinefavor.common.custom_data.global.TemplateData
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.network.TemplateNetHandlers
import aurocosh.divinefavor.common.network.TemplatePart
import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import net.minecraft.entity.player.EntityPlayerMP
import java.util.*

class MessageSendBlockTemplateServer : DivineServerMessage {
    lateinit var uuid: UUID
    var partIndex = 0
    var partCount = 0
    lateinit var bytes: ByteArray

    constructor()

    constructor(uuid: UUID, templatePart: TemplatePart) {
        this.uuid = uuid
        this.partIndex = templatePart.partIndex
        this.partCount = templatePart.partCount
        this.bytes = templatePart.bytes
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val templatePart = TemplatePart(partIndex, partCount, bytes)
        val blockTemplate = TemplateNetHandlers.serverHandler.receive(uuid, templatePart) ?: return

        val templateSavedData = serverPlayer.world[TemplateData]
        templateSavedData[uuid] = blockTemplate
    }
}
