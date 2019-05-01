package aurocosh.divinefavor.common.custom_data.player.data.aura.calling

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class CallingAuraDataSerializer : INbtSerializer<CallingAuraData> {

    override fun serialize(nbt: NBTTagCompound, instance: CallingAuraData) {
        nbt.setFloat(TAG_CALLING_CHANCE, instance.chance)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: CallingAuraData) {
        instance.chance = nbt.getFloat(TAG_CALLING_CHANCE)
    }

    companion object {
        private const val TAG_CALLING_CHANCE = "CallingChance"
    }
}
