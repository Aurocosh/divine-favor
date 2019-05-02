package aurocosh.divinefavor.common.global.dayClock

import java.util.*

class DayTimeAlarmComparator : Comparator<DayTimeAlarm> {
    override fun compare(alarm: DayTimeAlarm, alarm2: DayTimeAlarm): Int {
        if (alarm.time == alarm2.time)
            return 0
        return if (alarm.time < alarm2.time) -1 else 1
    }
}
