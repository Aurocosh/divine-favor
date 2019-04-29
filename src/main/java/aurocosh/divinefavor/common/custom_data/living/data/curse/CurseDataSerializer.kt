package aurocosh.divinefavor.common.custom_data.living.data.curse

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

class CurseDataSerializer : INbtSerializer<CurseData> {

    override fun serialize(nbt: NBTTagCompound, instance: CurseData) {
        nbt.setInteger(TAG_CURSE_COUNT, instance.curseCount)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: CurseData) {
        instance.curseCount = nbt.getInteger(TAG_CURSE_COUNT)
    }

    companion object {
        private const val TAG_CURSE_COUNT = "CurseCount"
    }
}
