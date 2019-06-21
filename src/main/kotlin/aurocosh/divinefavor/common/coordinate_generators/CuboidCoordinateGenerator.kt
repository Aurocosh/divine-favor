package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.lib.extensions.toBlockPos
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.util.math.BlockPos

class CuboidCoordinateGenerator : CachedCoordinateGenerator() {
    fun getCoordinates(directions: UtilPlayer.RelativeDirections, blockPos: BlockPos, up: Int, down: Int, left: Int, rigth: Int, depth: Int): List<BlockPos> {
        if (isCached(directions, blockPos, up, down, left, rigth, depth))
            return cachedCoordinates

        val surface = generateWallCoordinates(directions, blockPos, up, down, left, rigth)
        val forwardVec = directions.forward.opposite.directionVec.toBlockPos()
        cachedCoordinates = generateSequence(surface) { it.map(forwardVec::add) }.take(depth).flatten().toList()
        return cachedCoordinates
    }
}
