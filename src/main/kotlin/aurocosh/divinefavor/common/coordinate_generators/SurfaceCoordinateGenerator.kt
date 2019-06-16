package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.lib.coordinates.SurfaceFloodFiller
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class SurfaceCoordinateGenerator : CachedCoordinateGenerator() {
    fun getCoordinates(pos: BlockPos, count: Int, world: World, fuzzy: Boolean): List<BlockPos> {
        if(isCached(pos, count, world, fuzzy))
            return cachedCoordinates

        val filter = if (fuzzy) Blocks.AIR.defaultState else world.getBlockState(pos)

        val start = listOf(pos)
        val expansionDirs = BlockPosConstants.DIRECT_AND_DIAGONAL
        val blockPredicate: (BlockPos) -> Boolean = {
            if (fuzzy) true else world.getBlockState(it) == filter
        }

        val floodFiller = SurfaceFloodFiller(world)
        cachedCoordinates = floodFiller.floodFill(start, expansionDirs, blockPredicate, count)
        return cachedCoordinates
    }
}
