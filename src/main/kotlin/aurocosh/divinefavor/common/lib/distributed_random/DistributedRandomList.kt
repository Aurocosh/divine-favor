package aurocosh.divinefavor.common.lib.distributed_random

import java.util.*

open class DistributedRandomList<T> {
    private val distributedRandom: DistributedRandom = DistributedRandom()
    private val values: MutableList<T>

    val random: T?
        get() {
            if (values.isEmpty())
                return null
            val index = distributedRandom.randomNumber
            return values[index]
        }

    init {
        this.values = ArrayList()
    }

    fun add(value: T, distribution: Double) {
        val newIndex = values.size
        values.add(value)
        distributedRandom.addNumber(newIndex, distribution)
    }

    fun clear() {
        values.clear()
        distributedRandom.clear()
    }

    fun size(): Int {
        return values.size
    }

    operator fun get(index: Int): T {
        return values[index]
    }
}
