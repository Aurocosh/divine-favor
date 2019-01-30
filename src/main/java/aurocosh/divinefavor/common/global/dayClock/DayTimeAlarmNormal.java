package aurocosh.divinefavor.common.global.dayClock;

public class DayTimeAlarmNormal extends DayTimeAlarm {
    private final Runnable callback;

    public DayTimeAlarmNormal(int id, int time, boolean repeat, Runnable callback) {
        super(id, time, repeat);
        this.callback = callback;
    }

    public void activate() {
        callback.run();
    }
}
