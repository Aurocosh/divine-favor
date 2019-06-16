package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.toBlockPos
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos

class WallCoordinateGenerator : CachedCoordinateGenerator() {
    fun getCoordinates(player: EntityPlayer, blockPos: BlockPos, up: Int, down: Int, left: Int, rigth: Int): List<BlockPos> {
        if (isCached(blockPos, up, down, left, rigth))
            return cachedCoordinates

        val playerFacing = player.horizontalFacing
        val sideFacing = playerFacing.rotateAround(EnumFacing.Axis.Y)
        val firstSideVec = sideFacing.directionVec.toBlockPos()
        val secondSideVec = sideFacing.opposite.directionVec.toBlockPos()

        val upFront = generateSequence(blockPos, BlockPos::up).take(up)
        val downFront = generateSequence(blockPos, BlockPos::down).take(down)

        val centerColumn = (upFront + blockPos + downFront).toList()
        val firstSide = generateSequence(centerColumn){it.map(firstSideVec::add)}.flatten()
        val secondSide = generateSequence(centerColumn){it.map(secondSideVec::add)}.flatten()

        cachedCoordinates = (centerColumn.S + firstSide + secondSide).toList()
        return cachedCoordinates
    }
}
