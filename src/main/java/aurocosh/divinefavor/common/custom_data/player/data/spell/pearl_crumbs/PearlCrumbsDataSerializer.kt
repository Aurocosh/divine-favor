package aurocosh.divinefavor.common.custom_data.player.data.spell.pearl_crumbs

import aurocosh.divinefavor.common.lib.GlobalBlockPos
import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import aurocosh.divinefavor.common.util.UtilBlockPos
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos
import java.util.*

// Handles the actual read/write of the nbt.
class PearlCrumbsDataSerializer : INbtSerializer<PearlCrumbsData> {

    override fun serialize(nbt: NBTTagCompound, instance: PearlCrumbsData) {
        val globalBlockPos = instance.allPositions
        val posList = ArrayList<BlockPos>(globalBlockPos.size)
        val dimensions = IntArray(globalBlockPos.size)

        for (i in globalBlockPos.indices) {
            val pos = globalBlockPos[i]
            posList.add(pos.pos)
            dimensions[i] = pos.dimensionId
        }

        nbt.setIntArray(TAG_CRUMBS_POSITIONS, UtilBlockPos.serialize(posList))
        nbt.setIntArray(TAG_CRUMBS_DIMENSIONS, dimensions)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: PearlCrumbsData) {

        val posList = UtilBlockPos.deserialize(nbt.getIntArray(TAG_CRUMBS_POSITIONS))
        val dimensions = nbt.getIntArray(TAG_CRUMBS_DIMENSIONS)

        if (posList.size != dimensions.size)
            return

        val globalBlockPos = ArrayList<GlobalBlockPos>()
        for (i in posList.indices) {
            val pos = posList[i]
            val dimension = dimensions[i]
            globalBlockPos.add(GlobalBlockPos(pos, dimension))
        }
        instance.allPositions = globalBlockPos
    }

    companion object {
        private const val TAG_CRUMBS_POSITIONS = "CrumbsPositions"
        private const val TAG_CRUMBS_DIMENSIONS = "CrumbsDimensions"
    }
}
