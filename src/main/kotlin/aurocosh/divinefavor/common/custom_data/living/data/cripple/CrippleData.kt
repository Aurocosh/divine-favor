package aurocosh.divinefavor.common.custom_data.living.data.cripple

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.lib.SimpleCounter

class CrippleData {
    private val counter: SimpleCounter = SimpleCounter(ConfigArrow.cripple.cureRate)

    var cureTicks: Int
        get() = counter.count
        set(ticks) {
            counter.count = ticks
        }

    fun resetCureTimer() {
        counter.reset()
    }

    fun cureTick(): Boolean {
        return counter.count()
    }
}
