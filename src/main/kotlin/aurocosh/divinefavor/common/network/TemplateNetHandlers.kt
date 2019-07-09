package aurocosh.divinefavor.common.network

import aurocosh.divinefavor.common.network.message.client.template.MessageSendBlockTemplateClient
import aurocosh.divinefavor.common.network.message.sever.template.MessageSendBlockTemplateServer
import net.minecraft.entity.player.EntityPlayer
import java.util.*

object TemplateNetHandlers {
    val clientHandler = TemplateNetworkHandler(this::sendPartToClient)
    val serverHandler = TemplateNetworkHandler(this::sendPartToServer)

    private fun sendPartToClient(player: EntityPlayer, uuid: UUID, templatePart: TemplatePart) {
        MessageSendBlockTemplateClient(uuid, templatePart).sendTo(player)
    }

    private fun sendPartToServer(player: EntityPlayer, uuid: UUID, templatePart: TemplatePart) {
        MessageSendBlockTemplateServer(uuid, templatePart).send()
    }
}