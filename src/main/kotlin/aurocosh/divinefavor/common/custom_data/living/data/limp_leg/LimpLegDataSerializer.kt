package aurocosh.divinefavor.common.custom_data.living.data.limp_leg

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

class LimpLegDataSerializer : INbtSerializer<LimpLegData> {

    override fun serialize(nbt: NBTTagCompound, instance: LimpLegData) {
        nbt.setInteger(TAG_LIMP_LEG_CURE_TICKS, instance.cureTicks)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: LimpLegData) {
        instance.cureTicks = nbt.getInteger(TAG_LIMP_LEG_CURE_TICKS)
    }

    companion object {
        private const val TAG_LIMP_LEG_CURE_TICKS = "LimpLegCureTicks"
    }
}
