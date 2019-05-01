package aurocosh.divinefavor.common.custom_data.player.data.presence.energetic

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class EnergeticPresenceDataSerializer : INbtSerializer<EnergeticPresenceData> {

    override fun serialize(nbt: NBTTagCompound, instance: EnergeticPresenceData) {
        nbt.setInteger(TAG_TIME_RUNNING_ON_WATER, instance.count)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: EnergeticPresenceData) {
        instance.count = nbt.getInteger(TAG_TIME_RUNNING_ON_WATER)
    }

    companion object {
        private const val TAG_TIME_RUNNING_ON_WATER = "TimeRunningOnWater"
    }
}
