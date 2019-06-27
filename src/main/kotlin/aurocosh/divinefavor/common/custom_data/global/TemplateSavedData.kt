package aurocosh.divinefavor.common.custom_data.global

import aurocosh.divinefavor.common.item.spell_talismans.copy.BlockTemplate
import aurocosh.divinefavor.common.item.spell_talismans.copy.BlockTemplateSerializer
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
