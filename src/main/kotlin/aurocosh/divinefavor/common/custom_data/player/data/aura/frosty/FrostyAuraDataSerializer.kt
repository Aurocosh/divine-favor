package aurocosh.divinefavor.common.custom_data.player.data.aura.frosty

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class FrostyAuraDataSerializer : INbtSerializer<FrostyAuraData> {

    override fun serialize(nbt: NBTTagCompound, instance: FrostyAuraData) {
        nbt.setInteger(TAG_TIME_IS_SNOW_BIOME, instance.count)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: FrostyAuraData) {
        instance.count = nbt.getInteger(TAG_TIME_IS_SNOW_BIOME)
    }

    companion object {
        private const val TAG_TIME_IS_SNOW_BIOME = "TimeInSnowBiome"
    }
}
