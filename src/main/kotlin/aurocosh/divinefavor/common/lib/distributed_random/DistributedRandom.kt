package aurocosh.divinefavor.common.lib.distributed_random

import java.util.*

class DistributedRandom {
    private val distributions: MutableMap<Int, Double>
    private var distributionsSum: Double = 0.toDouble()

    val randomNumber: Int
        get() {
            val rand = Math.random()
            val ratio = 1.0f / distributionsSum
            var tempDist = 0.0
            for ((key, value) in distributions) {
                tempDist += value
                if (rand / ratio <= tempDist)
                    return key
            }
            return 0
        }

    init {
        distributions = HashMap()
    }

    fun addNumber(index: Int, distribution: Double) {
        distributionsSum -= distributions[index] ?: 0.0
        distributions[index] = distribution
        distributionsSum += distribution
    }

    fun clear() {
        distributions.clear()
        distributionsSum = 0.0
    }
}
