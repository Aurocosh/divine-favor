package aurocosh.divinefavor.common.global.dayClock

class DayTimeAlarmNormal(id: Int, time: Int, repeat: Boolean, private val callback: () -> Unit) : DayTimeAlarm(id, time, repeat) {

    override fun activate() {
        callback.invoke()
    }
}
