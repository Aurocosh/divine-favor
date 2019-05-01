package aurocosh.divinefavor.common.custom_data.player.data.presence.manipulative

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class ManipulativePresenceDataSerializer : INbtSerializer<ManipulativePresenceData> {

    override fun serialize(nbt: NBTTagCompound, instance: ManipulativePresenceData) {
        nbt.setFloat(TAG_MANIPULATIVE_CHANCE, instance.chance)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: ManipulativePresenceData) {
        instance.chance = nbt.getFloat(TAG_MANIPULATIVE_CHANCE)
    }

    companion object {
        private const val TAG_MANIPULATIVE_CHANCE = "ManipulativeChance"
    }
}
