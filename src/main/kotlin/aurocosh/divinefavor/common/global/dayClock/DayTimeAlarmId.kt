package aurocosh.divinefavor.common.global.dayClock

class DayTimeAlarmId(id: Int, time: Int, repeat: Boolean, private val callback: ClockAlarmCallback) : DayTimeAlarm(id, time, repeat) {

    override fun activate() {
        callback.run(id)
    }
}
