package aurocosh.divinefavor.common.global;

import aurocosh.divinefavor.common.util.UtilDayTime;

public class DayTimeAlarm {
    public final int id;
    public final int time;
    public final boolean repeat;
    public final Runnable callback;

    public DayTimeAlarm(int id, int time, boolean repeat, Runnable callback) {
        this.id = id;
        this.time = UtilDayTime.clampDay(time);
        this.repeat = repeat;
        this.callback = callback;
    }
}
