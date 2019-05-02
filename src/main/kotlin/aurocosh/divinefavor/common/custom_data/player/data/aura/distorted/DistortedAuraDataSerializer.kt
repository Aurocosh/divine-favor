package aurocosh.divinefavor.common.custom_data.player.data.aura.distorted

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class DistortedAuraDataSerializer : INbtSerializer<DistortedAuraData> {

    override fun serialize(nbt: NBTTagCompound, instance: DistortedAuraData) {
        nbt.setFloat(TAG_NEAR_ENDERMAN_CHANCE, instance.chance)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: DistortedAuraData) {
        instance.chance = nbt.getFloat(TAG_NEAR_ENDERMAN_CHANCE)
    }

    companion object {
        private const val TAG_NEAR_ENDERMAN_CHANCE = "NearEndermanChance"
    }
}
