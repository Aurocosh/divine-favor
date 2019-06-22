package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.lib.coordinates.SurfaceFloodFiller
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

fun generateSurfaceCoordinates(pos: BlockPos, count: Int, world: World, fuzzy: Boolean): List<BlockPos> {
    val filter = if (fuzzy) Blocks.AIR.defaultState else world.getBlockState(pos)

    val expansionDirs = BlockPosConstants.DIRECT_AND_DIAGONAL
    val blockPredicate: (BlockPos) -> Boolean = {
        if (fuzzy) true else world.getBlockState(it) == filter
    }

    val floodFiller = SurfaceFloodFiller(world)
    return floodFiller.floodFill(listOf(pos), expansionDirs, blockPredicate, count)
}