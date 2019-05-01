package aurocosh.divinefavor.common.custom_data.player.data.presence.industrious

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class IndustriousPresenceDataSerializer : INbtSerializer<IndustriousPresenceData> {

    override fun serialize(nbt: NBTTagCompound, instance: IndustriousPresenceData) {
        nbt.setInteger(TAG_ORE_TO_BREAK, instance.count)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: IndustriousPresenceData) {
        instance.count = nbt.getInteger(TAG_ORE_TO_BREAK)
    }

    companion object {
        private const val TAG_ORE_TO_BREAK = "OreToBreak"
    }
}
