package aurocosh.divinefavor.common.coordinate_generators

import net.minecraft.util.math.BlockPos

class ColumnCoordinateGenerator : CachedCoordinateGenerator() {
    fun getCoordinates(blockPos: BlockPos, count: Int): List<BlockPos> {
        if (isCached(blockPos, count))
            return cachedCoordinates
        cachedCoordinates = generateSequence(blockPos) { it.up() }.take(count).toList()
        return cachedCoordinates
    }
}
