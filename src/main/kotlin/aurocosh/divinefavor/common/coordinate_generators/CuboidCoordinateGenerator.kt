package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.lib.extensions.toBlockPos
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.util.math.BlockPos

class CuboidCoordinateGenerator : CachedCoordinateGenerator() {
    private val wallCoordinateGenerator = WallCoordinateGenerator()

    fun getCoordinates(directions: UtilPlayer.RelativeDirections, blockPos: BlockPos, up: Int, down: Int, left: Int, rigth: Int, depth: Int, count: Int): List<BlockPos> {
        if (isCached(directions, blockPos, up, down, left, rigth, depth, count))
            return cachedCoordinates

        val surface = wallCoordinateGenerator.getCoordinates(directions, blockPos, up, down, left, rigth, count)
        val forwardVec = directions.forward.opposite.directionVec.toBlockPos()
        cachedCoordinates = generateSequence(surface) { it.map(forwardVec::add) }.take(depth).flatten().toList()
        return cachedCoordinates
    }
}
