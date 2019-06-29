package aurocosh.divinefavor.common.network.message.sever

import aurocosh.divinefavor.common.block_templates.BlockTemplate
import aurocosh.divinefavor.common.block_templates.BlockTemplateSerializer
import aurocosh.divinefavor.common.custom_data.global.TemplateData
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.nbt.NBTTagCompound
import java.util.*

class MessageSendBlockTemplateServer : DivineServerMessage {
    lateinit var uuid: UUID
    lateinit var template: NBTTagCompound

    constructor()

    constructor(template: BlockTemplate) {
        this.uuid = template.uuid
        this.template = BlockTemplateSerializer.serialize(template)
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val templateSavedData = serverPlayer.world[TemplateData]
        templateSavedData[uuid] = BlockTemplateSerializer.deserialize(template)
    }
}
