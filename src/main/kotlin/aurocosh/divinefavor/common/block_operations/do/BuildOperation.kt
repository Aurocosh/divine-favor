package aurocosh.divinefavor.common.block_operations.`do`

import aurocosh.divinefavor.common.block_operations.undo.UndoOperation
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.tasks.base.BaseTask
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

abstract class BuildOperation {
    fun perform(player: EntityPlayer, world: World) {
        val (task, undoOperation) = getBuildingTask(player, world)
        task.addFinishAction {
            player.divinePlayerData.blockOperationsData.addUndoAction(undoOperation)
        }
        task.start()
    }

    protected abstract fun getBuildingTask(player: EntityPlayer, world: World): Pair<BaseTask, UndoOperation>
}