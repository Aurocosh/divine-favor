package aurocosh.divinefavor.common.custom_data.player.data.curse.corrosion

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound
import java.util.*

// Handles the actual read/write of the nbt.
class ArmorCorrosionDataSerializer : INbtSerializer<ArmorCorrosionData> {

    override fun serialize(nbt: NBTTagCompound, instance: ArmorCorrosionData) {
        val slots = instance.corrodedArmorSlots
        val slotsArray = IntArray(slots.size)
        for (i in slots.indices)
            slotsArray[i] = slots[i]
        nbt.setIntArray(TAG_CORRODED_SLOTS, slotsArray)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: ArmorCorrosionData) {
        val intArray = nbt.getIntArray(TAG_CORRODED_SLOTS)
        val slots = ArrayList<Int>(intArray.size)
        for (slot in intArray)
            slots.add(slot)
        instance.corrodedArmorSlots = slots
    }

    companion object {
        private const val TAG_CORRODED_SLOTS = "CorrodedSlots"
    }
}
