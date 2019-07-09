package aurocosh.divinefavor.common.network.message.client.template

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.custom_data.global.TemplateData
import aurocosh.divinefavor.common.lib.EmptyConst.invalidUUID
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.network.TemplateNetHandlers
import aurocosh.divinefavor.common.network.TemplatePart
import aurocosh.divinefavor.common.network.message.base.DivineClientMessage
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class MessageSendBlockTemplateClient : DivineClientMessage {
    var uuid: UUID = invalidUUID()
    var partIndex = 0
    var partCount = 0
    var bytes: ByteArray = ByteArray(0)

    constructor()

    constructor(uuid: UUID, templatePart: TemplatePart) {
        this.uuid = uuid
        this.partIndex = templatePart.partIndex
        this.partCount = templatePart.partCount
        this.bytes = templatePart.bytes
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val templatePart = TemplatePart(partIndex, partCount, bytes)
        val blockTemplate = TemplateNetHandlers.clientHandler.receive(uuid, templatePart) ?: return

        val player = DivineFavor.proxy.clientPlayer
        val templateSavedData = player.world[TemplateData]
        templateSavedData[uuid] = blockTemplate
    }
}
