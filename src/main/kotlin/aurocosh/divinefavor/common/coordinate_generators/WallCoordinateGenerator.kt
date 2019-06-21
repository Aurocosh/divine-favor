package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.toBlockPos
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos

fun generateWallCoordinates(directions: UtilPlayer.RelativeDirections, blockPos: BlockPos, up: Int, down: Int, left: Int, right: Int): List<BlockPos> {
    val upVec = directions.up.directionVec.toBlockPos()
    val downVec = directions.down.directionVec.toBlockPos()
    val leftVec = directions.left.directionVec.toBlockPos()
    val rightVec = directions.right.directionVec.toBlockPos()

    val upPart = generateSequence(blockPos.add(upVec), upVec::add).take(up)
    val downPart = generateSequence(blockPos.add(downVec), downVec::add).take(down)

    val center = (upPart + downPart + sequenceOf(blockPos)).toList()

    val leftSide = generateSequence(center.map(leftVec::add)) { it.map(leftVec::add) }.take(right).flatten()
    val rightSide = generateSequence(center.map(rightVec::add)) { it.map(rightVec::add) }.take(left).flatten()

    return (center.S + leftSide + rightSide).toList()
}
