package aurocosh.divinefavor.common.custom_data.player.data.aura.visceral

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class VisceralAuraDataSerializer : INbtSerializer<VisceralAuraData> {

    override fun serialize(nbt: NBTTagCompound, instance: VisceralAuraData) {
        nbt.setFloat(TAG_VISCERAL_CHANCE, instance.chance)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: VisceralAuraData) {
        instance.chance = nbt.getFloat(TAG_VISCERAL_CHANCE)
    }

    companion object {
        private const val TAG_VISCERAL_CHANCE = "VisceralChance"
    }
}
