package aurocosh.divinefavor.common.lib

import aurocosh.divinefavor.common.util.UtilDayTime
import aurocosh.divinefavor.common.util.UtilMath

class TimePeriod(start: Int, stop: Int) {
    val start: Int = UtilMath.clamp(start, 0, UtilDayTime.TICKS_IN_DAY)
    val stop: Int = UtilMath.clamp(stop, 0, UtilDayTime.TICKS_IN_DAY)

    /**
     * @param value value between 0 and UtilDayTime.TICKS_IN_DAY
     * @return if value is belongs to this time period
     */
    fun isDayTimeInRange(value: Int): Boolean {
        return if (start <= stop) value in start..stop else start <= value && value <= UtilDayTime.TICKS_IN_DAY || value in 0..stop
    }
}
