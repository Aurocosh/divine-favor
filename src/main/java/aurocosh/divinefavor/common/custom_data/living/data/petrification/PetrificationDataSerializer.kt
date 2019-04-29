package aurocosh.divinefavor.common.custom_data.living.data.petrification

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

class PetrificationDataSerializer : INbtSerializer<PetrificationData> {

    override fun serialize(nbt: NBTTagCompound, instance: PetrificationData) {
        nbt.setInteger(TAG_PETRIFICATION_CURE_TICKS, instance.cureTicks)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: PetrificationData) {
        instance.cureTicks = nbt.getInteger(TAG_PETRIFICATION_CURE_TICKS)
    }

    companion object {
        private const val TAG_PETRIFICATION_CURE_TICKS = "PetrificationCureTicks"
    }
}
