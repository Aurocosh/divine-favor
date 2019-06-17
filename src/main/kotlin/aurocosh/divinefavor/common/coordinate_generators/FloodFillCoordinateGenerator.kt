package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.lib.coordinates.FloodFiller
import aurocosh.divinefavor.common.lib.coordinates.SurfaceFloodFiller
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class FloodFillCoordinateGenerator : CachedCoordinateGenerator() {
    fun getCoordinates(pos: BlockPos, count: Int, world: World, fuzzy: Boolean): List<BlockPos> {
        if(isCached(pos, count, world, fuzzy))
            return cachedCoordinates

        val airState = Blocks.AIR.defaultState
        val filter = if (fuzzy) Blocks.AIR.defaultState else world.getBlockState(pos)

        val start = listOf(pos)
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
        cachedCoordinates = floodFiller.floodFill(start, expansionDirs, blockPredicate, count)
        return cachedCoordinates
    }
}
