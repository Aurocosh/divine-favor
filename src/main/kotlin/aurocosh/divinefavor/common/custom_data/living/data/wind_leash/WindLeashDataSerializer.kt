package aurocosh.divinefavor.common.custom_data.living.data.wind_leash

import aurocosh.divinefavor.common.lib.extensions.getVec3d
import aurocosh.divinefavor.common.lib.extensions.setVec3d
import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

class WindLeashDataSerializer : INbtSerializer<WindLeashData> {

    override fun serialize(nbt: NBTTagCompound, instance: WindLeashData) {
        nbt.setVec3d(TAG_WIND_LEASH, instance.vector)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: WindLeashData) {
        instance.vector = nbt.getVec3d(TAG_WIND_LEASH)
    }

    companion object {
        private const val TAG_WIND_LEASH = "WindLeash"
    }
}
