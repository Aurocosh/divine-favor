package aurocosh.divinefavor.common.lib

import java.util.*

class LimitedMap<K, V>(private val maxCapacity: Int) : HashMap<K, V>(maxCapacity) {
    private val additionOrder = LinkedList<K>()

    override fun remove(key: K): V? {
        val value = super.remove(key)
        additionOrder.remove(key)
        return value
    }

    override fun put(key: K, value: V): V? {
        additionOrder.add(key)
        val result = super.put(key, value)
        removeExcess()
        return result
    }

    override fun putAll(from: Map<out K, V>) {
        additionOrder.addAll(from.keys)
        super.putAll(from)
        removeExcess()
    }

    override fun putIfAbsent(key: K, value: V): V? {
        if (!containsKey(key))
            additionOrder.add(key)
        val result = super.putIfAbsent(key, value)
        removeExcess()
        return result
    }

    private fun removeExcess() {
        while (size > maxCapacity)
            remove(additionOrder.removeLast())
    }
}