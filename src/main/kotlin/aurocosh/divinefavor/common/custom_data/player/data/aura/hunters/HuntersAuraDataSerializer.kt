package aurocosh.divinefavor.common.custom_data.player.data.aura.hunters

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class HuntersAuraDataSerializer : INbtSerializer<HuntersAuraData> {

    override fun serialize(nbt: NBTTagCompound, instance: HuntersAuraData) {
        nbt.setFloat(TAG_HUNTERS_CHANCE, instance.chance)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: HuntersAuraData) {
        instance.chance = nbt.getFloat(TAG_HUNTERS_CHANCE)
    }

    companion object {
        private const val TAG_HUNTERS_CHANCE = "HuntersChance"
    }
}
