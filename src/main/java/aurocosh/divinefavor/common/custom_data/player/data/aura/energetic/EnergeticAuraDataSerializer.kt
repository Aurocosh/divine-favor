package aurocosh.divinefavor.common.custom_data.player.data.aura.energetic

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class EnergeticAuraDataSerializer : INbtSerializer<EnergeticAuraData> {

    override fun serialize(nbt: NBTTagCompound, instance: EnergeticAuraData) {
        nbt.setFloat(TAG_ENERGETIC_CHANCE, instance.chance)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: EnergeticAuraData) {
        instance.chance = nbt.getFloat(TAG_ENERGETIC_CHANCE)
    }

    companion object {
        private const val TAG_ENERGETIC_CHANCE = "EnergeticChance"
    }
}
