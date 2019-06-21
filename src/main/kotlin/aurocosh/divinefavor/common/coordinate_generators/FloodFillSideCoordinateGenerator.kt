package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.lib.coordinates.FloodFiller
import aurocosh.divinefavor.common.lib.extensions.inverse
import aurocosh.divinefavor.common.lib.extensions.toBlockPos
import net.minecraft.init.Blocks
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class FloodFillSideCoordinateGenerator : CachedCoordinateGenerator() {
    fun getCoordinates(pos: BlockPos, count: Int, world: World, fuzzy: Boolean, facing: EnumFacing): List<BlockPos> {
        if(isCached(pos, count, world, fuzzy, facing))
            return cachedCoordinates

        val airState = Blocks.AIR.defaultState
        val filter = if (fuzzy) Blocks.AIR.defaultState else world.getBlockState(pos)

        val expansionDirs = BlockPosConstants.DIRECT_NEIGHBOURS.toMutableList()
        val shift = facing.opposite.directionVec.toBlockPos()
        expansionDirs.remove(shift)
        expansionDirs.remove(shift.inverse())

        val predicate = { pos: BlockPos ->
            val state = world.getBlockState(pos)
            when {
                state == airState -> false
                !fuzzy && state != filter -> false
                else -> state.isSideSolid(world, pos, facing) && world.isAirBlock(pos.offset(facing))
            }
        }

        val floodFiller = FloodFiller()
        cachedCoordinates = floodFiller.floodFill(listOf(pos), expansionDirs, predicate, count)
        return cachedCoordinates
    }
}
