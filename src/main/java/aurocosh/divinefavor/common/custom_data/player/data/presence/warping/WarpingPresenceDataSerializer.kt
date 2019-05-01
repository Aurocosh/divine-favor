package aurocosh.divinefavor.common.custom_data.player.data.presence.warping

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class WarpingPresenceDataSerializer : INbtSerializer<WarpingPresenceData> {

    override fun serialize(nbt: NBTTagCompound, instance: WarpingPresenceData) {
        nbt.setFloat(TAG_WARPING_CHANCE, instance.chance)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: WarpingPresenceData) {
        instance.chance = nbt.getFloat(TAG_WARPING_CHANCE)
    }

    companion object {
        private const val TAG_WARPING_CHANCE = "WarpingChance"
    }
}
