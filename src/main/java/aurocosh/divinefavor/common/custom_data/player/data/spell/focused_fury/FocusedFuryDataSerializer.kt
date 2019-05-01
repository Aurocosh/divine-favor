package aurocosh.divinefavor.common.custom_data.player.data.spell.focused_fury

import aurocosh.divinefavor.common.lib.extensions.toResourceLocation
import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class FocusedFuryDataSerializer : INbtSerializer<FocusedFuryData> {

    override fun serialize(nbt: NBTTagCompound, instance: FocusedFuryData) {
        nbt.setString(TAG_FOCUSED_FURY, instance.mobTypeId.toString())
    }

    override fun deserialize(nbt: NBTTagCompound, instance: FocusedFuryData) {
        instance.mobTypeId = nbt.getString(TAG_FOCUSED_FURY).toResourceLocation()
    }

    companion object {
        private const val TAG_FOCUSED_FURY = "FocusedFury"
    }
}
