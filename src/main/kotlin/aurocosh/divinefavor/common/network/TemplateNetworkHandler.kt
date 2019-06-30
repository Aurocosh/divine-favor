package aurocosh.divinefavor.common.network

import aurocosh.divinefavor.common.block_templates.BlockTemplate
import aurocosh.divinefavor.common.block_templates.BlockTemplateSerializer
import aurocosh.divinefavor.common.util.UtilSerialize
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.CompressedStreamTools
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.HashMap

class TemplateNetworkHandler(val sender: (player: EntityPlayer, UUID, TemplatePart) -> Unit) {
    fun send(player: EntityPlayer, blockTemplate: BlockTemplate) {
        val compound = BlockTemplateSerializer.serialize(blockTemplate)
        val outputStream = ByteArrayOutputStream()
        CompressedStreamTools.writeCompressed(compound, outputStream)
        val byteArray = outputStream.toByteArray()
        val splitArray = UtilSerialize.splitArray(byteArray, maxPayloadSize)

        for ((index, value) in splitArray.withIndex()) {
            val templatePart = TemplatePart(index, splitArray.size, value)
            sender.invoke(player, blockTemplate.uuid, templatePart)
        }
    }

    fun receive(uuid: UUID, templatePart: TemplatePart): BlockTemplate? {
        val partAssembler = templateAssemblers.computeIfAbsent(uuid) { TemplatePartAssembler(templatePart.partCount) }
        partAssembler.addPart(templatePart)
        val blockTemplate = partAssembler.assemble() ?: return null
        templateAssemblers.remove(uuid)
        return blockTemplate
    }

    private val maxPayloadSize = Short.MAX_VALUE - 200
    private val templateAssemblers = HashMap<UUID, TemplatePartAssembler>()
}