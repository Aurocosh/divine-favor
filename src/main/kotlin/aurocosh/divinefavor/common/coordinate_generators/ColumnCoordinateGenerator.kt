package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.lib.extensions.toBlockPos
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos

fun generateLineCoordinates(blockPos: BlockPos, facing: EnumFacing, count: Int): List<BlockPos> {
    val direction = facing.directionVec.toBlockPos()
    return generateSequence(blockPos, direction::add).take(count).toList()
}
