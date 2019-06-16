package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.toBlockPos
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos

class WallCoordinateGenerator : CachedCoordinateGenerator() {
    fun getCoordinates(playerFacing: EnumFacing, blockPos: BlockPos, height: Int, left: Int, rigth: Int, count: Int): List<BlockPos> {
        if (isCached(playerFacing, blockPos, height, left, rigth))
            return cachedCoordinates

        val sideFacing = playerFacing.rotateAround(EnumFacing.Axis.Y)
        val firstSideVec = sideFacing.directionVec.toBlockPos()
        val secondSideVec = sideFacing.opposite.directionVec.toBlockPos()

        val centerColumn = generateSequence(blockPos, BlockPos::up).take(height).toList()

        val firstSide = generateSequence(centerColumn.map(firstSideVec::add)) { it.map(firstSideVec::add) }.take(rigth).flatten()
        val secondSide = generateSequence(centerColumn.map(secondSideVec::add)) { it.map(secondSideVec::add) }.take(left).flatten()

        cachedCoordinates = (centerColumn.S + firstSide + secondSide).take(count).toList()
        return cachedCoordinates
    }
}
