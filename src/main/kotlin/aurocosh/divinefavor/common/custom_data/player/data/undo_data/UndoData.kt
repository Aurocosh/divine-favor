package aurocosh.divinefavor.common.custom_data.player.data.undo_data

import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.lib.LimitedStack
import aurocosh.divinefavor.common.undo.UndoOperation

class UndoData {
    private val undoActions = LimitedStack<UndoOperation>(ConfigGeneral.undoActionsCount)

    fun hasActions() = undoActions.isNotEmpty()
    fun addAction(element: UndoOperation) = undoActions.push(element)
    fun getAction() = undoActions.pop()
}
