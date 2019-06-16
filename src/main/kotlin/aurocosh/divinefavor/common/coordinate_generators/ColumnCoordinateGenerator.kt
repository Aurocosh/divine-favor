package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.lib.extensions.toBlockPos
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos

class ColumnCoordinateGenerator : CachedCoordinateGenerator() {
    fun getCoordinates(blockPos: BlockPos, facing: EnumFacing, count: Int): List<BlockPos> {
        if (isCached(blockPos, facing, count))
            return cachedCoordinates
        val direction = facing.directionVec.toBlockPos()
        cachedCoordinates = generateSequence(blockPos, direction::add).take(count).toList()
        return cachedCoordinates
    }
}
