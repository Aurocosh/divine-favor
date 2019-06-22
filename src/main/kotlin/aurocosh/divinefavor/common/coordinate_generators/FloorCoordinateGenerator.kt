package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.toBlockPos
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos

fun generateFloorCoordinates(playerFacing: EnumFacing, blockPos: BlockPos, front: Int, back: Int, left: Int, rigth: Int): List<BlockPos> {
    val frontVec = playerFacing.directionVec.toBlockPos()
    val backVec = playerFacing.opposite.directionVec.toBlockPos()

    val sideFacing = playerFacing.rotateAround(EnumFacing.Axis.Y)
    val firstSideVec = sideFacing.directionVec.toBlockPos()
    val secondSideVec = sideFacing.opposite.directionVec.toBlockPos()

    val frontPart = generateSequence(blockPos.add(frontVec), frontVec::add).take(front)
    val backPart = generateSequence(blockPos.add(backVec), backVec::add).take(back)

    val centerRow = (frontPart + blockPos + backPart).toList()
    val firstSide = generateSequence(centerRow.map(firstSideVec::add)) { it.map(firstSideVec::add) }.take(rigth).flatten()
    val secondSide = generateSequence(centerRow.map(secondSideVec::add)) { it.map(secondSideVec::add) }.take(left).flatten()

    return (centerRow.S + firstSide + secondSide).toList()
}