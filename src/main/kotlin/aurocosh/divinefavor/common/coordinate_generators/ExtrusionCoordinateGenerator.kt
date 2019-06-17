package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.lib.extensions.inverse
import aurocosh.divinefavor.common.lib.extensions.toBlockPos
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ExtrusionCoordinateGenerator : CachedCoordinateGenerator() {
    fun getCoordinates(shapeBase: BlockPos, origin: BlockPos, world: World, facing: EnumFacing, surface: Int, length: Int): List<BlockPos> {
        if (isCached(shapeBase, origin, world, facing, surface, length))
            return cachedCoordinates

        val expansionDirs = BlockPosConstants.DIRECT_NEIGHBOURS.toMutableList()
        val shift = facing.opposite.directionVec.toBlockPos()
        expansionDirs.remove(shift)
        expansionDirs.remove(shift.inverse())

        val predicate = { pos: BlockPos ->
            val state = world.getBlockState(pos)
            state.isSideSolid(world, pos, facing) && world.isAirBlock(pos.offset(facing))
        }

        val shapeShift = shapeBase.inverse().add(origin)
        val shape = UtilCoordinates.floodFill(listOf(shapeBase), expansionDirs, predicate, surface).map(shapeShift::add)

        val extrusionVec = facing.directionVec.toBlockPos()
        cachedCoordinates = generateSequence(shape) { it.map(extrusionVec::add) }.take(length).flatten().toList()
        return cachedCoordinates
    }
}
