package aurocosh.divinefavor.common.lib

import java.util.*

class LimitedStack<T>(private val limit: Int) {
    private val values = LinkedList<T>()

    fun size() = values.size
    fun clear() = values.clear()
    fun isEmpty() = values.isEmpty()
    fun isNotEmpty() = values.isNotEmpty()

    fun push(element: T) {
        values.push(element)
        trimExcess()
    }

    fun pop() = values.pop()

    private fun trimExcess() {
        while (values.size > limit)
            values.removeLast()
    }
}