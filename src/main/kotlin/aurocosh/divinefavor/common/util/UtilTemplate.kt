package aurocosh.divinefavor.common.util

import aurocosh.divinefavor.common.block_templates.BlockTemplate
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.TemplateNetHandlers
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncTemplateClient
import aurocosh.divinefavor.common.network.message.sever.syncing.MessageSyncTemplateServer
import net.minecraft.entity.player.EntityPlayer
import java.util.*

object UtilTemplate {
    fun setCurrent(player: EntityPlayer, template: UUID) {
        player.divinePlayerData.templateData.currentTemplate = template
        if (player.world.isRemote)
            MessageSyncTemplateServer(template).send()
        else
            MessageSyncTemplateClient(template).sendTo(player)
    }

    fun setCurrent(player: EntityPlayer, template: BlockTemplate) {
        setCurrent(player, template.uuid)
        if (player.world.isRemote)
            TemplateNetHandlers.serverHandler.send(player, template)
        else
            TemplateNetHandlers.clientHandler.send(player, template)
    }
}
