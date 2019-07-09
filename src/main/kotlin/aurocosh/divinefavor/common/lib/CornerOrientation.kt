package aurocosh.divinefavor.common.lib

import aurocosh.divinefavor.common.lib.math.Vector2i
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos

enum class CornerOrientation(val pos: BlockPos) {
    UpSouthEast(BlockPos(1, 1, 1)),
    UpSouthWest(BlockPos(-1, 1, 1)),
    UpNorthWest(BlockPos(-1, 1, -1)),
    UpNorthEast(BlockPos(1, 1, -1)),
    DownSouthEast(BlockPos(1, -1, 1)),
    DownSouthWest(BlockPos(-1, -1, 1)),
    DownNorthWest(BlockPos(-1, -1, -1)),
    DownNorthEast(BlockPos(1, -1, -1));

    fun mirror(axis: EnumFacing.Axis): CornerOrientation {
        return when (axis) {
            EnumFacing.Axis.X -> posToCornerMap[BlockPos(-pos.x, pos.y, pos.z)] ?: this
            EnumFacing.Axis.Y -> posToCornerMap[BlockPos(pos.x, -pos.y, pos.z)] ?: this
            else -> posToCornerMap[BlockPos(pos.x, pos.y, -pos.z)] ?: this
        }
    }

    fun rotate(axis: EnumFacing.Axis, direction: RotationDirection, count: Int = 1): CornerOrientation {
        val surfaceVec = getSurfaceVec(axis)
        val newSurfaceVec = Companion.rotate(surfaceVec, direction, count)
        val cornerVec = getCornerVec(axis, newSurfaceVec)
        return posToCornerMap[cornerVec] ?: this
    }

    private fun getSurfaceVec(axis: EnumFacing.Axis): Vector2i {
        return when (axis) {
            EnumFacing.Axis.X -> Vector2i(pos.y, pos.z)
            EnumFacing.Axis.Y -> Vector2i(pos.x, pos.z)
            else -> Vector2i(pos.x, pos.y)
        }
    }

    private fun getCornerVec(axis: EnumFacing.Axis, vec: Vector2i): BlockPos {
        return when (axis) {
            EnumFacing.Axis.X -> BlockPos(pos.x, vec.x, vec.y)
            EnumFacing.Axis.Y -> BlockPos(vec.x, pos.y, vec.y)
            else -> BlockPos(vec.x, vec.y, pos.z)
        }
    }

    companion object : IIndexedEnum<CornerOrientation> {
        override val indexer: EnumIndexer<CornerOrientation> = EnumIndexer(values())
        private val posToCornerMap = HashMap<BlockPos, CornerOrientation>()
        private val nextNodes = HashMap<Vector2i, Vector2i>()
        private val prevNodes = HashMap<Vector2i, Vector2i>()

        init {
            values().forEach { posToCornerMap[it.pos] = it }


            val southEast = Vector2i(1, 1)
            val southWest = Vector2i(-1, 1)
            val northWest = Vector2i(-1, -1)
            val northEast = Vector2i(1, -1)

            nextNodes[southEast] = southWest
            nextNodes[southWest] = northWest
            nextNodes[northWest] = northEast
            nextNodes[northEast] = southEast

            nextNodes.forEach { prevNodes[it.value] = it.key }
        }

        private fun rotate(dir: Vector2i, direction: RotationDirection, count: Int = 1): Vector2i {
            return when {
                count % 4 == 0 -> return dir
                count % 2 == 0 -> return dir.inverse()
                direction == RotationDirection.Clockwise -> nextNodes[dir] ?: dir
                else -> prevNodes[dir] ?: dir
            }
        }
    }
}