package aurocosh.divinefavor.common.global.dayClock;

public class DayTimeAlarmId extends DayTimeAlarm {
    private final ClockAlarmCallback callback;

    public DayTimeAlarmId(int id, int time, boolean repeat, ClockAlarmCallback callback) {
        super(id, time, repeat);
        this.callback = callback;
    }

    public void activate() {
        callback.run(id);
    }
}
