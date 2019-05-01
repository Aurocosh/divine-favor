package aurocosh.divinefavor.common.custom_data.player.data.spell.molten_skin

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class MoltenSkinDataSerializer : INbtSerializer<MoltenSkinData> {

    override fun serialize(nbt: NBTTagCompound, instance: MoltenSkinData) {
        nbt.setInteger(TAG_TIME_OUTSIDE_LAVA, instance.ticks)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: MoltenSkinData) {
        instance.setTime(nbt.getInteger(TAG_TIME_OUTSIDE_LAVA))
    }

    companion object {
        private const val TAG_TIME_OUTSIDE_LAVA = "TimeOutsideLava"
    }
}
