package aurocosh.divinefavor.common.custom_data.living.data.petrification

import aurocosh.divinefavor.common.config.common.ConfigCurses
import aurocosh.divinefavor.common.lib.LoopedCounter
import aurocosh.divinefavor.common.lib.SimpleCounter

class PetrificationData {
    private val cureCounter: SimpleCounter = SimpleCounter(ConfigCurses.petrification.cureRate)
    private val damageCounter: LoopedCounter = LoopedCounter(ConfigCurses.petrification.damageRate)

    var cureTicks: Int
        get() = cureCounter.count
        set(ticks) {
            cureCounter.count = ticks
        }

    fun resetCureTimer() {
        cureCounter.reset()
    }

    fun cureTick(): Boolean {
        return cureCounter.count()
    }

    fun damageTick(): Boolean {
        return damageCounter.tick()
    }
}
