package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.util.math.BlockPos

class HollowSphereCoordinateGenerator : CachedCoordinateGenerator() {
    fun getCoordinates(blockPos: BlockPos, internalRadius: Int, externalRadius: Int): List<BlockPos> {
        if (isCached(blockPos, internalRadius, externalRadius))
            return cachedCoordinates
        cachedCoordinates = UtilCoordinates.getSphereOutline(blockPos, internalRadius, externalRadius).toList()
        return cachedCoordinates
    }
}
