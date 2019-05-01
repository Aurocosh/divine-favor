package aurocosh.divinefavor.common.util

import net.minecraft.util.math.BlockPos
import java.util.*

object UtilBlockPos {
    fun getMinCoordinates(first: BlockPos, second: BlockPos): BlockPos {
        val xMin = Math.min(first.x, second.x)
        val yMin = Math.min(first.y, second.y)
        val zMin = Math.min(first.z, second.z)
        return BlockPos(xMin, yMin, zMin)
    }

    fun getMaxCoordinates(first: BlockPos, second: BlockPos): BlockPos {
        val xMax = Math.max(first.x, second.x)
        val yMax = Math.max(first.y, second.y)
        val zMax = Math.max(first.z, second.z)
        return BlockPos(xMax, yMax, zMax)
    }

    fun serialize(posCollection: Collection<BlockPos>): IntArray {
        val array = IntArray(posCollection.size * 3)
        var i = 0
        for (pos in posCollection) {
            array[i++] = pos.x
            array[i++] = pos.y
            array[i++] = pos.z
        }

        return array
    }

    fun deserialize(array: IntArray): ArrayList<BlockPos> {
        val posCount = array.size / 3
        val posArrayList = ArrayList<BlockPos>()

        var i = 0
        var j = 0
        while (i < posCount) {
            val x = array[j++]
            val y = array[j++]
            val z = array[j++]
            posArrayList.add(BlockPos(x, y, z))
            i++
        }
        return posArrayList
    }
}
