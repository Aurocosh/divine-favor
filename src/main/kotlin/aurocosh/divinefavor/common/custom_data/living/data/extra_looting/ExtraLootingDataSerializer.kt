package aurocosh.divinefavor.common.custom_data.living.data.extra_looting

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

class ExtraLootingDataSerializer : INbtSerializer<ExtraLootingData> {
    override fun serialize(nbt: NBTTagCompound, instance: ExtraLootingData) {
        nbt.setInteger(TAG_EXTRA_LOOTING, instance.extraLooting)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: ExtraLootingData) {
        instance.extraLooting = nbt.getInteger(TAG_EXTRA_LOOTING)
    }

    companion object {
        private const val TAG_EXTRA_LOOTING = "ExtraLooting"
    }
}
