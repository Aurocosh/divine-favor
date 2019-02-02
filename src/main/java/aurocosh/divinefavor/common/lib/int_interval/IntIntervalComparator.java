package aurocosh.divinefavor.common.lib.int_interval;

import java.util.Comparator;

public class IntIntervalComparator implements Comparator<IntInterval> {
    @Override
    public int compare(IntInterval o1, IntInterval o2) {
        if (o1.start == o2.start)
            return 0;
        return o1.start < o2.start ? -1 : 1;
    }
}
