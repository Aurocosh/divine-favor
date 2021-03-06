package aurocosh.divinefavor.common.block_operations.`do`

import aurocosh.divinefavor.common.block_operations.undo.UndoDestruction
import aurocosh.divinefavor.common.block_operations.undo.UndoOperation
import aurocosh.divinefavor.common.tasks.BlockPlacingTask
import aurocosh.divinefavor.common.tasks.base.BaseTask
import aurocosh.divinefavor.common.util.UtilTick
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class DestructionOperation(private val coordinates: List<BlockPos>) : BuildOperation() {
    override fun getBuildingTask(player: EntityPlayer, world: World): Pair<BaseTask, UndoOperation> {
        val breakTime = UtilTick.secondsToTicks(2f)

        val finalCoordinates = coordinates.shuffled()
        val blocksPerTick = if (breakTime > finalCoordinates.size) 1 else finalCoordinates.size / breakTime
        val placingTask = BlockPlacingTask(finalCoordinates, Blocks.AIR.defaultState, player, blocksPerTick)

        val destroyedStates = finalCoordinates.map { Pair(it, world.getBlockState(it)) }
        val undoDestruction = UndoDestruction(destroyedStates, this, blocksPerTick)
        return Pair(placingTask, undoDestruction)
    }
}