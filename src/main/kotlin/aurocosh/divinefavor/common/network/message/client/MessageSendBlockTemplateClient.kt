package aurocosh.divinefavor.common.network.message.client

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.block_templates.BlockTemplate
import aurocosh.divinefavor.common.block_templates.BlockTemplateSerializer
import aurocosh.divinefavor.common.custom_data.global.TemplateData
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.network.message.base.DivineClientMessage
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class MessageSendBlockTemplateClient : DivineClientMessage {
    lateinit var uuid: UUID
    lateinit var template: NBTTagCompound

    constructor()

    constructor(template: BlockTemplate) {
        this.uuid = template.uuid
        this.template = BlockTemplateSerializer.serialize(template)
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer
        val templateSavedData = player.world[TemplateData]
        templateSavedData[uuid] = BlockTemplateSerializer.deserialize(template)
    }
}
