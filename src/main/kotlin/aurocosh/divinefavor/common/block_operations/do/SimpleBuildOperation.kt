package aurocosh.divinefavor.common.block_operations.`do`

import aurocosh.divinefavor.common.block_operations.undo.UndoBuild
import aurocosh.divinefavor.common.block_operations.undo.UndoOperation
import aurocosh.divinefavor.common.tasks.BlockPlacingTask
import aurocosh.divinefavor.common.tasks.base.BaseTask
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilTick
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class SimpleBuildOperation(private val coordinates: List<BlockPos>, private val state: IBlockState) : BuildOperation() {
    override fun getBuildingTask(player: EntityPlayer, world: World): Pair<BaseTask, UndoOperation> {
        val validCoordinates = UtilBlock.getBlocksForPlacement(player, world, state, coordinates)

        val buildTime = UtilTick.secondsToTicks(2f)
        val blocksPerTick = if (buildTime > validCoordinates.size) 1 else validCoordinates.size / buildTime
        val blockPlacingTask = BlockPlacingTask(validCoordinates, state, player, blocksPerTick)
        val undoBuild = UndoBuild(coordinates, this, blocksPerTick)
        return Pair(blockPlacingTask, undoBuild)
    }
}