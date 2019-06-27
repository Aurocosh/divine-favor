package aurocosh.divinefavor.common.custom_data.player.data.templates

import aurocosh.divinefavor.common.lib.EmptyConst.emptyUUID
import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class TemplateDataSerializer : INbtSerializer<TemplateData> {

    override fun serialize(nbt: NBTTagCompound, instance: TemplateData) {
        nbt.setUniqueId(tagCurrentTemplate, instance.currentTemplate)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: TemplateData) {
        instance.currentTemplate = nbt.getUniqueId(tagCurrentTemplate) ?: emptyUUID()
    }

    companion object {
        private const val tagCurrentTemplate = "CurrentTemplate"
    }
}
