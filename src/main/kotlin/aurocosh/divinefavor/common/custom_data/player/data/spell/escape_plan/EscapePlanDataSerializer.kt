package aurocosh.divinefavor.common.custom_data.player.data.spell.escape_plan

import aurocosh.divinefavor.common.lib.GlobalBlockPos
import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos

// Handles the actual read/write of the nbt.
class EscapePlanDataSerializer : INbtSerializer<EscapePlanData> {

    override fun serialize(nbt: NBTTagCompound, instance: EscapePlanData) {
        val pos = BlockPos.fromLong(nbt.getLong(TAG_ESCAPE_POSITION))
        val dimension = nbt.getInteger(TAG_ESCAPE_DIMENSION)
        instance.globalPosition = GlobalBlockPos(pos, dimension)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: EscapePlanData) {
        val pos = instance.globalPosition
        nbt.setLong(TAG_ESCAPE_POSITION, pos!!.pos.toLong())
        nbt.setInteger(TAG_ESCAPE_DIMENSION, pos.dimensionId)
    }

    companion object {
        private const val TAG_ESCAPE_POSITION = "EscapePosition"
        private const val TAG_ESCAPE_DIMENSION = "EscapeDimension"
    }
}
