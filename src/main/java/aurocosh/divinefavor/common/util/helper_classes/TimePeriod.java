package aurocosh.divinefavor.common.util.helper_classes;

import aurocosh.divinefavor.common.util.UtilMath;

public class TimePeriod {
    private int start;
    private int stop;

    public TimePeriod(int start, int stop) {
        this.start = UtilMath.clamp(start,0,24000);
        this.stop = UtilMath.clamp(stop,0,24000);
    }

    public TimePeriod(int start, int stop, int timeInHour) {
        this.start = UtilMath.clamp(start * timeInHour,0,24000);
        this.stop = UtilMath.clamp(stop * timeInHour,0,24000);
    }

    /**
     *
     * @param value value between 0 and 20000
     * @return if value is belongs to this time period
     */
    public boolean isDayTimeInRange(int value){
        if(start <= stop)
            return start <= value && value <= stop;
        return (start <= value && value <= 20000) || (0 <= value && value <= stop);
    }
}
