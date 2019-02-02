package aurocosh.divinefavor.common.lib.int_interval;

import java.util.ArrayList;
import java.util.List;

public class IntInterval {
    public final int start;
    public final int stop;

    public IntInterval(int start, int stop) {
        this.start = start;
        this.stop = Math.max(start, stop);
    }

    public boolean isInsideInclusive(int value) {
        return value >= start && value <= stop;
    }

    public static List<IntInterval> optimize(List<IntInterval> intervals) {
        if (intervals.size() <= 1)
            return intervals;
        intervals.sort(new IntIntervalComparator());
        List<IntInterval> optimal = new ArrayList<>(intervals.size());
        IntInterval previousInterval = intervals.get(0);
        optimal.add(previousInterval);
        for (int i = 1; i < intervals.size(); i++) {
            IntInterval interval = intervals.get(i);
            if (interval.start < previousInterval.stop)
                previousInterval = new IntInterval(previousInterval.stop + 1, interval.stop);
            else
                previousInterval = interval;
            optimal.add(previousInterval);
        }

        return optimal;
    }
}
