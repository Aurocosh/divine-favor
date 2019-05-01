package aurocosh.divinefavor.common.custom_data.player.data.curse.evil_eye

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class EvilEyeDataSerializer : INbtSerializer<EvilEyeData> {

    override fun serialize(nbt: NBTTagCompound, instance: EvilEyeData) {
        nbt.setInteger(TAG_EVIL_EYE_SEVERITY, instance.severity)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: EvilEyeData) {
        instance.severity = nbt.getInteger(TAG_EVIL_EYE_SEVERITY)
    }

    companion object {
        private val TAG_EVIL_EYE_SEVERITY = "EVIL_EYE_SEVERITY"
    }
}
