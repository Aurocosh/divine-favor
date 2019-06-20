package aurocosh.divinefavor.common.coordinate_generators

import aurocosh.divinefavor.common.config.common.ConfigTool
import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.lib.extensions.isLeaves
import aurocosh.divinefavor.common.lib.extensions.isWood
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class TreeCoordinateGenerator : CachedCoordinateGenerator() {
    fun getCoordinates(pos: BlockPos, world: World, count: Int): List<BlockPos> {
        if (isCached(pos, count, world))
            return cachedCoordinates
        cachedCoordinates = detectTree(pos, world, count)
        return cachedCoordinates
    }

    private fun detectTree(pos: BlockPos, world: World, count: Int): List<BlockPos> {
        val logs = UtilCoordinates.floodFill(listOf(pos), BlockPosConstants.DIRECT_NEIGHBOURS, world::isWood, count)
        if (logs.isEmpty())
            return logs

        val possibleLeaves = logs.map { logPos -> BlockPosConstants.DIRECT_NEIGHBOURS.map(logPos::add) }.flatten().toSet()
        val leaves = UtilCoordinates.floodFill(possibleLeaves, BlockPosConstants.DIRECT_NEIGHBOURS, world::isLeaves, ConfigTool.fellTree.minLeafCount)
        if (leaves.size < ConfigTool.fellTree.minLeafCount)
            return ArrayList()
        return logs
    }
}
