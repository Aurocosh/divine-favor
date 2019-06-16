package aurocosh.divinefavor.common.lib.coordinates

import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.util.UtilPredicate
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class SurfaceFloodFiller(private val world: World) : FloodFiller() {
    private val airState = Blocks.AIR.defaultState
    private val airPositions = HashSet<BlockPos>()
    private val solidPositions = HashSet<BlockPos>()

    override fun reset() {
        super.reset()
        airPositions.clear()
        solidPositions.clear()
    }

    override fun floodFill(start: Collection<BlockPos>, expansionDirs: Collection<BlockPos>, predicate: (BlockPos) -> Boolean, limit: Int): List<BlockPos> {
        val combinedPredicate = UtilPredicate.and(predicate, this::isValid)
        return super.floodFill(start, expansionDirs, combinedPredicate, limit)
    }

    private fun isValid(pos: BlockPos): Boolean {
        if (!isSolid(pos))
            return false
        return BlockPosConstants.DIRECT_NEIGHBOURS.S.map(pos::add).any { !isSolid(it) }
    }

    private fun isSolid(pos: BlockPos): Boolean {
        if (airPositions.contains(pos))
            return false
        if (solidPositions.contains(pos))
            return true
        val state = world.getBlockState(pos)
        val isAir = state == airState
        if (isAir) {
            explored.add(pos)
            airPositions.add(pos)
        } else
            solidPositions.add(pos)
        return !isAir
    }
}