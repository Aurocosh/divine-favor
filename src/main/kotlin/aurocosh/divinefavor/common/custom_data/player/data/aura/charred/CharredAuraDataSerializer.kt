package aurocosh.divinefavor.common.custom_data.player.data.aura.charred

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class CharredAuraDataSerializer : INbtSerializer<CharredAuraData> {

    override fun serialize(nbt: NBTTagCompound, instance: CharredAuraData) {
        nbt.setInteger(TAG_FIRES_TO_IGNITE, instance.count)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: CharredAuraData) {
        instance.count = nbt.getInteger(TAG_FIRES_TO_IGNITE)
    }

    companion object {
        private const val TAG_FIRES_TO_IGNITE = "FiresToIgnite"
    }
}
