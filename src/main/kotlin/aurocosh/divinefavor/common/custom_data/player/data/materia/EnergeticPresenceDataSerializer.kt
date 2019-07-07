package aurocosh.divinefavor.common.custom_data.player.data.materia

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class MaterialPresenceDataSerializer : INbtSerializer<MaterialPresenceData> {

    override fun serialize(nbt: NBTTagCompound, instance: MaterialPresenceData) {
        nbt.setFloat(TAG_MATERIAL_CHANCE, instance.chance)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: MaterialPresenceData) {
        instance.chance = nbt.getFloat(TAG_MATERIAL_CHANCE)
    }

    companion object {
        private const val TAG_MATERIAL_CHANCE = "MaterialChance"
    }
}
