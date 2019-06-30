package aurocosh.divinefavor.common.network

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.block_templates.BlockTemplate
import aurocosh.divinefavor.common.block_templates.BlockTemplateSerializer
import aurocosh.divinefavor.common.util.UtilSerialize
import net.minecraft.nbt.CompressedStreamTools
import java.io.ByteArrayInputStream

class TemplatePart(val partIndex: Int, val partCount: Int, val bytes: ByteArray)

class TemplatePartAssembler(private val partCount: Int) {
    private val parts = ArrayList<TemplatePart>(partCount)
    private val receivedParts = HashSet<Int>(partCount)

    fun addPart(templatePart: TemplatePart) {
        if (templatePart.partIndex < 0) {
            DivineFavor.logger.error("Negative part index")
            return
        }
        if (templatePart.partIndex >= partCount) {
            DivineFavor.logger.error("Invalid part index")
            return
        }
        if (receivedParts.contains(templatePart.partIndex)) {
            DivineFavor.logger.error("Duplicate part")
            return
        }

        receivedParts.add(templatePart.partIndex)
        parts.add(templatePart)
    }

    val isComplete get() = receivedParts.size == partCount

    fun assemble(): BlockTemplate? {
        if (!isComplete)
            return null
        parts.sortBy(TemplatePart::partIndex)
        val arrayParts = parts.map(TemplatePart::bytes)
        val completeArray = UtilSerialize.joinByteArrays(arrayParts)
        val inputStream = ByteArrayInputStream(completeArray)
        val compound = CompressedStreamTools.readCompressed(inputStream)
        val blockTemplate = BlockTemplateSerializer.deserialize(compound)
        return blockTemplate
    }
}