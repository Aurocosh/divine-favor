package aurocosh.divinefavor.common.global.dayClock

@FunctionalInterface
interface ClockAlarmCallback {
    fun run(alarmId: Int)
}
