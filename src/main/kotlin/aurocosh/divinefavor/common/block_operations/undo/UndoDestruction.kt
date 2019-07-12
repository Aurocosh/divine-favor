package aurocosh.divinefavor.common.block_operations.undo

import aurocosh.divinefavor.common.block_operations.`do`.BuildOperation
import aurocosh.divinefavor.common.tasks.BlockPlacingComplexTask
import aurocosh.divinefavor.common.tasks.base.BaseTask
import aurocosh.divinefavor.common.util.UtilTick
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class UndoDestruction(private val coordinateStates: List<Pair<BlockPos, IBlockState>>, operation: BuildOperation, blocksPerTick: Int) : UndoOperation(operation, blocksPerTick) {
    override fun getUndoTask(player: EntityPlayer, world: World): BaseTask {
        val time = UtilTick.secondsToTicks(2f)
        val blocksPerTick = if (time > coordinateStates.size) 1 else coordinateStates.size / time
        return BlockPlacingComplexTask(coordinateStates, player, blocksPerTick)
    }
}