package aurocosh.divinefavor.common.custom_data.player.data.presence.towering

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class ToweringPresenceDataSerializer : INbtSerializer<ToweringPresenceData> {

    override fun serialize(nbt: NBTTagCompound, instance: ToweringPresenceData) {
        nbt.setInteger(TAG_CURSE_TIME, instance.curseTime)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: ToweringPresenceData) {
        instance.curseTime = nbt.getInteger(TAG_CURSE_TIME)
    }

    companion object {
        private const val TAG_CURSE_TIME = "CurseTime"
    }
}
