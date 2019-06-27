package aurocosh.divinefavor.common.custom_data.global

import aurocosh.divinefavor.common.block_templates.BlockTemplate
import aurocosh.divinefavor.common.block_templates.BlockTemplateSerializer
import aurocosh.divinefavor.common.lib.EmptyConst.emptyUUID
import net.minecraft.nbt.NBTTagCompound
import java.util.*

class TemplateSavedData(name: String) : MapSavedData<UUID, BlockTemplate>(name, keyReader, NBTTagCompound::setUniqueId, BlockTemplateSerializer::deserialize, BlockTemplateSerializer::serialize) {
    companion object {
        private val keyReader: (NBTTagCompound, String) -> UUID = { compound, tag ->
            compound.getUniqueId(tag) ?: emptyUUID()
        }
    }
}
