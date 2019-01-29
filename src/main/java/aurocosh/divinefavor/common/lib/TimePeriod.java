package aurocosh.divinefavor.common.lib;

import aurocosh.divinefavor.common.util.UtilMath;
import aurocosh.divinefavor.common.util.UtilDayTime;

public class TimePeriod {
    private final int start;
    private final int stop;

    public TimePeriod(int start, int stop) {
        this.start = UtilMath.clamp(start, 0, UtilDayTime.TICKS_IN_DAY);
        this.stop = UtilMath.clamp(stop, 0, UtilDayTime.TICKS_IN_DAY);
    }

    /**
     * @param value value between 0 and 20000
     * @return if value is belongs to this time period
     */
    public boolean isDayTimeInRange(int value) {
        if (start <= stop)
            return start <= value && value <= stop;
        return (start <= value && value <= UtilDayTime.TICKS_IN_DAY) || (0 <= value && value <= stop);
    }
}
