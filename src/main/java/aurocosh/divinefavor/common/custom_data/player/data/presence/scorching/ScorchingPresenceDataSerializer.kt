package aurocosh.divinefavor.common.custom_data.player.data.presence.scorching

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class ScorchingPresenceDataSerializer : INbtSerializer<ScorchingPresenceData> {

    override fun serialize(nbt: NBTTagCompound, instance: ScorchingPresenceData) {
        nbt.setFloat(TAG_SCORCHING_CHANCE, instance.chance)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: ScorchingPresenceData) {
        instance.chance = nbt.getFloat(TAG_SCORCHING_CHANCE)
    }

    companion object {
        private const val TAG_SCORCHING_CHANCE = "ScorchingChance"
    }
}
