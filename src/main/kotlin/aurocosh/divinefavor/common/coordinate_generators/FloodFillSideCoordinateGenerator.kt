package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.lib.coordinates.FloodFiller
import aurocosh.divinefavor.common.lib.extensions.inverse
import aurocosh.divinefavor.common.lib.extensions.toBlockPos
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

fun generateSideCoordinates(pos: BlockPos, count: Int, world: World, facing: EnumFacing, predicate: (IBlockState) -> Boolean): List<BlockPos> {
    val airState = Blocks.AIR.defaultState
    val expansionDirs = BlockPosConstants.DIRECT_NEIGHBOURS.toMutableList()
    val shift = facing.opposite.directionVec.toBlockPos()
    expansionDirs.remove(shift)
    expansionDirs.remove(shift.inverse())

    val finalPredicate = { blockPos: BlockPos ->
        val state = world.getBlockState(blockPos)
        if (state == airState)
            false
        else if (!state.isSideSolid(world, blockPos, facing))
            false
        else if (!world.isAirBlock(blockPos.offset(facing)))
            false
        else
            predicate.invoke(state)
    }

    val floodFiller = FloodFiller()
    return floodFiller.floodFill(listOf(pos), expansionDirs, finalPredicate, count)
}
