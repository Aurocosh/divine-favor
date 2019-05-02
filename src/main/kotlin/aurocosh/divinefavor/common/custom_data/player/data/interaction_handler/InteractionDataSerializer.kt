package aurocosh.divinefavor.common.custom_data.player.data.interaction_handler

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import aurocosh.divinefavor.common.util.UtilBlockPos
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class InteractionDataSerializer : INbtSerializer<InteractionData> {

    override fun serialize(nbt: NBTTagCompound, instance: InteractionData) {
        val clickedArray = UtilBlockPos.serialize(instance.lastClickedPositions)
        nbt.setIntArray(TAG_LAST_CLICKED_POSITIONS, clickedArray)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: InteractionData) {
        val posArray = nbt.getIntArray(TAG_LAST_CLICKED_POSITIONS)
        instance.lastClickedPositions = UtilBlockPos.deserialize(posArray)
    }

    companion object {
        private const val TAG_LAST_CLICKED_POSITIONS = "LastClickedPositions"
    }
}
