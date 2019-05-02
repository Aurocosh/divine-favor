package aurocosh.divinefavor.common.custom_data.player.data.spell.gills

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class GillsDataSerializer : INbtSerializer<GillsData> {

    override fun serialize(nbt: NBTTagCompound, instance: GillsData) {
        nbt.setInteger(TAG_TIME_OUTSIDE_WATER, instance.ticks)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: GillsData) {
        instance.setTime(nbt.getInteger(TAG_TIME_OUTSIDE_WATER))
    }

    companion object {
        private const val TAG_TIME_OUTSIDE_WATER = "TimeOutsideWater"
    }
}
