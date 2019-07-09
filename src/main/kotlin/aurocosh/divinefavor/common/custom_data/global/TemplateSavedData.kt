package aurocosh.divinefavor.common.custom_data.global

import aurocosh.divinefavor.common.block_templates.BlockTemplate
import aurocosh.divinefavor.common.block_templates.BlockTemplateSerializer
import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.lib.CornerOrientation
import aurocosh.divinefavor.common.lib.EmptyConst.invalidUUID
import aurocosh.divinefavor.common.lib.LimitedMap
import aurocosh.divinefavor.common.lib.extensions.getMap
import aurocosh.divinefavor.common.lib.extensions.setMap
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.storage.WorldSavedData
import java.util.*
import kotlin.collections.HashMap

class TemplateSavedData(name: String) : WorldSavedData(name) {
    private val temporaryData = LimitedMap<UUID, BlockTemplate>(ConfigGeneral.temporaryTemplateLimit)
    private val persistentData = LimitedMap<UUID, BlockTemplate>(ConfigGeneral.persistentTemplateLimit)

    private val variations = LimitedMap<UUID, MutableMap<CornerOrientation, UUID>>(ConfigGeneral.templateVariationLimit);

    fun addVariation(key: UUID, variationKey: UUID, orientation: CornerOrientation) {
        val variationMap = variations.computeIfAbsent(key) { HashMap() }
        variations[variationKey] = variationMap
        val template = get(key) ?: return
        variationMap[template.orientation] = key
        variationMap[orientation] = variationKey
    }

    fun getVariation(key: UUID, orientation: CornerOrientation): UUID? {
        return variations[key]?.get(orientation)
    }

    operator fun get(key: UUID) = temporaryData[key] ?: persistentData[key]
    fun contains(key: UUID) = temporaryData.contains(key) || persistentData.contains(key)

    operator fun set(key: UUID, value: BlockTemplate) {
        temporaryData[key] = value
    }

    fun makePersistent(key: UUID) {
        val template = temporaryData[key] ?: return
        persistentData[key] = template
        markDirty()
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        compound.setMap(tagTemplates, persistentData, NBTTagCompound::setUniqueId, BlockTemplateSerializer::serialize)
        return compound
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        val map = compound.getMap(tagTemplates, keyReader, BlockTemplateSerializer::deserialize)
        persistentData.clear()
        persistentData.putAll(map)
    }

    companion object {
        private const val tagTemplates = "Templates"
        private val keyReader: (NBTTagCompound, String) -> UUID = { compound, tag ->
            compound.getUniqueId(tag) ?: invalidUUID()
        }
    }
}
