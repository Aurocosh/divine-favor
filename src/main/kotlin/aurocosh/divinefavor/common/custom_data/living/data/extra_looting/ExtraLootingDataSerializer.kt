package aurocosh.divinefavor.common.custom_data.living.data.extra_looting

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

class ExtraLootingDataSerializer : INbtSerializer<ExtraLootingData> {
    override fun serialize(nbt: NBTTagCompound, instance: ExtraLootingData) {
        nbt.setInteger(TAG_EXTRA_LOOTING, instance.extraLooting)
        nbt.setBoolean(TAG_IS_LOOT_DENIED, instance.isLootDenied)
        nbt.setBoolean(TAG_IS_EXPERIENCE_DENIED, instance.isExperienceDenied)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: ExtraLootingData) {
        instance.extraLooting = nbt.getInteger(TAG_EXTRA_LOOTING)
        instance.isLootDenied = nbt.getBoolean(TAG_IS_LOOT_DENIED)
        instance.isExperienceDenied = nbt.getBoolean(TAG_IS_EXPERIENCE_DENIED)
    }

    companion object {
        private const val TAG_EXTRA_LOOTING = "ExtraLooting"
        private const val TAG_IS_LOOT_DENIED = "IsLootDenied"
        private const val TAG_IS_EXPERIENCE_DENIED = "IsExperienceDenied"
    }
}
