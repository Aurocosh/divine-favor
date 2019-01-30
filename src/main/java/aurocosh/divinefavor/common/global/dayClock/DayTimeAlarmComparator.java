package aurocosh.divinefavor.common.global.dayClock;

import java.util.Comparator;

public class DayTimeAlarmComparator implements Comparator<DayTimeAlarm> {
    @Override
    public int compare(DayTimeAlarm o1, DayTimeAlarm o2) {
        if (o1.time == o2.time)
            return 0;
        return o1.time < o2.time ? -1 : 1;
    }
}
