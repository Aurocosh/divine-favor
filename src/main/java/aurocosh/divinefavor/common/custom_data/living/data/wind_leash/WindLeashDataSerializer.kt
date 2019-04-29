package aurocosh.divinefavor.common.custom_data.living.data.wind_leash

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import aurocosh.divinefavor.common.util.UtilNbt
import net.minecraft.nbt.NBTTagCompound

class WindLeashDataSerializer : INbtSerializer<WindLeashData> {

    override fun serialize(nbt: NBTTagCompound, instance: WindLeashData) {
        UtilNbt.setVec3d(nbt, TAG_WIND_LEASH, instance.getVector())
    }

    override fun deserialize(nbt: NBTTagCompound, instance: WindLeashData) {
        instance.setVector(UtilNbt.getVec3d(nbt, TAG_WIND_LEASH))
    }

    companion object {
        private const val TAG_WIND_LEASH = "WindLeash"
    }
}
