package aurocosh.divinefavor.common.custom_data.living.data.limp_leg

import aurocosh.divinefavor.common.lib.SimpleCounter
import aurocosh.divinefavor.common.util.UtilTick

class LimpLegData {
    private val counter: SimpleCounter

    var cureTicks: Int
        get() = counter.count
        set(ticks) {
            counter.count = ticks
        }

    init {
        this.counter = SimpleCounter(SQUAT_TIME)
    }

    fun resetCureTimer() {
        counter.reset()
    }

    fun cureTick(): Boolean {
        return counter.count()
    }

    companion object {
        val SQUAT_TIME = UtilTick.secondsToTicks(5f)
    }
}
