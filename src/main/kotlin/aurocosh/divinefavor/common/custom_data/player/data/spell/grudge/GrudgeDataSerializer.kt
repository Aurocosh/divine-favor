package aurocosh.divinefavor.common.custom_data.player.data.spell.grudge

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ResourceLocation

// Handles the actual read/write of the nbt.
class GrudgeDataSerializer : INbtSerializer<GrudgeData> {

    override fun serialize(nbt: NBTTagCompound, instance: GrudgeData) {
        nbt.setString(TAG_GRUDGE, instance.mobTypeId.toString())
    }

    override fun deserialize(nbt: NBTTagCompound, instance: GrudgeData) {
        val value = nbt.getString(TAG_GRUDGE)
        instance.mobTypeId = ResourceLocation(value)
    }

    companion object {
        private const val TAG_GRUDGE = "Grudge"
    }
}
