package aurocosh.divinefavor.common.custom_data.player.data.curse.red_fury

import aurocosh.divinefavor.common.lib.extensions.getVec3d
import aurocosh.divinefavor.common.lib.extensions.setVec3d
import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

class RedFuryDataSerializer : INbtSerializer<RedFuryData> {

    override fun serialize(nbt: NBTTagCompound, instance: RedFuryData) {
        val compound = NBTTagCompound()
        compound.setVec3d(TAG_VECTOR, instance.vector)
        nbt.setTag(TAG_RED_FURY, compound)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: RedFuryData) {
        if (!nbt.hasKey(TAG_RED_FURY))
            return
        val compound = nbt.getTag(TAG_RED_FURY) as NBTTagCompound
        instance.vector = compound.getVec3d(TAG_VECTOR)
    }

    companion object {
        private const val TAG_RED_FURY = "RedFury"
        private const val TAG_VECTOR = "Vector"
    }
}
