package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.lib.extensions.directionPos
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.util.math.BlockPos

fun generateCylinderCoordinates(directions: UtilPlayer.RelativeDirections, blockPos: BlockPos, radius: Int, length: Int): List<BlockPos> {
    val wallCoordinates = generateWallCoordinates(directions, blockPos, radius, radius, radius, radius)
    val radiusSq = radius * radius
    val circleCoordinates = wallCoordinates.filter { pos -> blockPos.distanceSq(pos) <= radiusSq }.toList()

    val extrusionVec = directions.back.directionPos
    return generateSequence(circleCoordinates) { it.map(extrusionVec::add) }.take(length).flatten().toList()
}
