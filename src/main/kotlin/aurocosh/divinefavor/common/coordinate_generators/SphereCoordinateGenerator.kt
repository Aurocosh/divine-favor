package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.util.math.BlockPos

class SphereCoordinateGenerator : CachedCoordinateGenerator() {
    fun getCoordinates(blockPos: BlockPos, radius: Int): List<BlockPos> {
        if (isCached(blockPos, radius))
            return cachedCoordinates
        cachedCoordinates = UtilCoordinates.getBlocksInSphere(blockPos, radius).toList()
        return cachedCoordinates
    }
}
