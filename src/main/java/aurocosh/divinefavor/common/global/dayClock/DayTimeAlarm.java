package aurocosh.divinefavor.common.global.dayClock;

import aurocosh.divinefavor.common.util.UtilDayTime;

public abstract class DayTimeAlarm {
    public final int id;
    public final int time;
    public final boolean repeat;

    public DayTimeAlarm(int id, int time, boolean repeat) {
        this.id = id;
        this.time = UtilDayTime.clampDay(time);
        this.repeat = repeat;
    }

    public abstract void activate();

    @Override
    public String toString() {
        return "DayTimeAlarm{" +
                "id=" + id +
                ", time=" + time +
                ", repeat=" + repeat +
                '}';
    }
}
