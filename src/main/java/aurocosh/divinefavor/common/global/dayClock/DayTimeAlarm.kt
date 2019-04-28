package aurocosh.divinefavor.common.global.dayClock

import aurocosh.divinefavor.common.util.UtilDayTime

abstract class DayTimeAlarm(val id: Int, time: Int, val repeat: Boolean) {
    val time: Int = UtilDayTime.clampDay(time)

    abstract fun activate()

    override fun toString(): String {
        return "DayTimeAlarm{id=$id, time=$time, repeat=$repeat}"
    }
}
