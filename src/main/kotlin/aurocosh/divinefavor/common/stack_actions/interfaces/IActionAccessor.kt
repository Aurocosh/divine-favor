package aurocosh.divinefavor.common.stack_actions.interfaces

import aurocosh.divinefavor.common.stack_actions.StackAction

interface IActionAccessor {
    val list: List<StackAction>

    fun get(index: Int): StackAction?
    fun get(name: String): StackAction?

    fun exist(index: Int): Boolean
    fun exist(name: String): Boolean
}