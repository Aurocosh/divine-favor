package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.lib.coordinates.FloodFiller
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

fun generateFloodFillCoordinates(pos: BlockPos, count: Int, world: World, fuzzy: Boolean): List<BlockPos> {
    val airState = Blocks.AIR.defaultState
    val filter = if (fuzzy) Blocks.AIR.defaultState else world.getBlockState(pos)

    val expansionDirs = BlockPosConstants.DIRECT_AND_DIAGONAL
    val blockPredicate: (BlockPos) -> Boolean = {
        val state = world.getBlockState(it)
        when {
            state == airState -> false
            fuzzy -> true
            else -> state == filter
        }
    }

    val floodFiller = FloodFiller()
    return floodFiller.floodFill(listOf(pos), expansionDirs, blockPredicate, count)
}
