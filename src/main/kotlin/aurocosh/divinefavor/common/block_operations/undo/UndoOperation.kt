package aurocosh.divinefavor.common.block_operations.undo

import aurocosh.divinefavor.common.block_operations.`do`.BuildOperation
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.tasks.base.BaseTask
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

abstract class UndoOperation(private val operation: BuildOperation, val blocksPerTick: Int) {
    fun perform(player: EntityPlayer, world: World) {
        val task = getUndoTask(player, world)
        task.addFinishAction {
            player.divinePlayerData.blockOperationsData.addRedoAction(operation)
        }
        task.start()
    }

    protected abstract fun getUndoTask(player: EntityPlayer, world: World): BaseTask
}