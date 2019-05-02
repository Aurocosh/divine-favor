package aurocosh.divinefavor.common.custom_data.player.data.presence.furious

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class FuriousPresenceDataSerializer : INbtSerializer<FuriousPresenceData> {

    override fun serialize(nbt: NBTTagCompound, instance: FuriousPresenceData) {
        nbt.setInteger(TAG_WOOD_TO_BREAK, instance.count)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: FuriousPresenceData) {
        instance.count = nbt.getInteger(TAG_WOOD_TO_BREAK)
    }

    companion object {
        private const val TAG_WOOD_TO_BREAK = "WoodToBreak"
    }
}
