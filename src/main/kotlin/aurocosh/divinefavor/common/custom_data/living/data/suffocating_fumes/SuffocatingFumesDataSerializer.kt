package aurocosh.divinefavor.common.custom_data.living.data.suffocating_fumes

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class SuffocatingFumesDataSerializer : INbtSerializer<SuffocatingFumesData> {

    override fun serialize(nbt: NBTTagCompound, instance: SuffocatingFumesData) {
        nbt.setInteger(TAG_SUFFOCATING_FUME_Y, instance.y)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: SuffocatingFumesData) {
        instance.y = nbt.getInteger(TAG_SUFFOCATING_FUME_Y)
    }

    companion object {
        private const val TAG_SUFFOCATING_FUME_Y = "SuffocatingFumeY"
    }
}
