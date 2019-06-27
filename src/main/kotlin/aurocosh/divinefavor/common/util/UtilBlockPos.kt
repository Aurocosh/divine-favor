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

    // TODO replace
    fun relativePositionToInt(origin: BlockPos, pos: BlockPos): Int {
        val px = pos.x - origin.x and 0xff shl 16
        val py = pos.y - origin.y and 0xff shl 8
        val pz = pos.z - origin.z and 0xff
        return px + py + pz
    }

    // TODO replace
    fun relativeIntToPosition(origin: BlockPos, value: Int): BlockPos {
        val x = origin.x + (value and 0xff0000 shr 16).toByte()
        val y = origin.y + (value and 0x00ff00 shr 8).toByte()
        val z = origin.z + (value and 0x0000ff).toByte()
        return BlockPos(x, y, z)
    }

    fun blockPosToInt(pos: BlockPos): Int {
        val x = pos.x and 0xff shl 16
        val y = pos.y and 0xff shl 8
        val z = pos.z and 0xff
        return x + y + z
    }

    fun intToBlockPos(value: Int): BlockPos {
        val x = (value and 0xff0000 shr 16)
        val y = (value and 0x00ff00 shr 8)
        val z = (value and 0x0000ff)
        return BlockPos(x, y, z)
    }
}
