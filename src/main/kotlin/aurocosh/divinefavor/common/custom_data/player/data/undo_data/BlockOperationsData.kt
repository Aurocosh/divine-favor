package aurocosh.divinefavor.common.custom_data.player.data.undo_data

import aurocosh.divinefavor.common.block_operations.`do`.BuildOperation
import aurocosh.divinefavor.common.block_operations.undo.UndoOperation
import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.lib.LimitedStack

class BlockOperationsData {
    private val redoActions = LimitedStack<BuildOperation>(ConfigGeneral.redoActionsCount)
    private val undoActions = LimitedStack<UndoOperation>(ConfigGeneral.undoActionsCount)

    fun hasUndoActions() = undoActions.isNotEmpty()
    fun addUndoAction(element: UndoOperation) = undoActions.push(element)
    fun getUndoAction() = undoActions.pop()
    fun clearUndoActions() = undoActions.clear()

    fun hasRedoActions() = redoActions.isNotEmpty()
    fun addRedoAction(element: BuildOperation) = redoActions.push(element)
    fun getRedoAction() = redoActions.pop()
    fun clearRedoActions() = redoActions.clear()
}
