package aurocosh.divinefavor.common.lib;

import aurocosh.divinefavor.common.util.UtilMath;

public class TimePeriod {
    private final int start;
    private final int stop;

    public TimePeriod(int start, int stop) {
        this.start = UtilMath.clamp(start, 0, 24000);
        this.stop = UtilMath.clamp(stop, 0, 24000);
    }

    public static TimePeriod fromHours(int start, int stop) {
        return new TimePeriod(start * 1000, stop * 1000);
    }

    /**
     * @param value value between 0 and 20000
     * @return if value is belongs to this time period
     */
    public boolean isDayTimeInRange(int value) {
        if (start <= stop)
            return start <= value && value <= stop;
        return (start <= value && value <= 24000) || (0 <= value && value <= stop);
    }
}
