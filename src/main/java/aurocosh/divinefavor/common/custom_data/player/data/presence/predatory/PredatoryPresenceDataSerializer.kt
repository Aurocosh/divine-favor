package aurocosh.divinefavor.common.custom_data.player.data.presence.predatory

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class PredatoryPresenceDataSerializer : INbtSerializer<PredatoryPresenceData> {

    override fun serialize(nbt: NBTTagCompound, instance: PredatoryPresenceData) {
        nbt.setInteger(TAG_MOBS_TO_HUNT, instance.count)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: PredatoryPresenceData) {
        instance.count = nbt.getInteger(TAG_MOBS_TO_HUNT)
    }

    companion object {
        private const val TAG_MOBS_TO_HUNT = "MobsToHunt"
    }
}
