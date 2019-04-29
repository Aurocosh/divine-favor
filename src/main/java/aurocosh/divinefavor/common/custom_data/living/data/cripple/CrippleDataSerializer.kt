package aurocosh.divinefavor.common.custom_data.living.data.cripple

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

class CrippleDataSerializer : INbtSerializer<CrippleData> {

    override fun serialize(nbt: NBTTagCompound, instance: CrippleData) {
        nbt.setInteger(TAG_CRIPPLE_CURE_TICKS, instance.cureTicks)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: CrippleData) {
        instance.cureTicks = nbt.getInteger(TAG_CRIPPLE_CURE_TICKS)
    }

    companion object {
        private const val TAG_CRIPPLE_CURE_TICKS = "CrippleCureTicks"
    }
}
