package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.lib.extensions.toBlockPos
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.util.math.BlockPos

fun generateCuboid(directions: UtilPlayer.RelativeDirections, blockPos: BlockPos, up: Int, down: Int, left: Int, rigth: Int, depth: Int): List<BlockPos> {
    val surface = generateWallCoordinates(directions, blockPos, up, down, left, rigth)
    val forwardVec = directions.forward.opposite.directionVec.toBlockPos()
    return generateSequence(surface) { it.map(forwardVec::add) }.take(depth).flatten().toList()
}
