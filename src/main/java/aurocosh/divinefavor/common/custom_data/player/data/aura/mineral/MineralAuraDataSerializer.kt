package aurocosh.divinefavor.common.custom_data.player.data.aura.mineral

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class MineralAuraDataSerializer : INbtSerializer<MineralAuraData> {

    override fun serialize(nbt: NBTTagCompound, instance: MineralAuraData) {
        nbt.setInteger(TAG_STONE_TO_BREAK, instance.count)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: MineralAuraData) {
        instance.count = nbt.getInteger(TAG_STONE_TO_BREAK)
    }

    companion object {
        private const val TAG_STONE_TO_BREAK = "StoneToBreak"
    }
}
