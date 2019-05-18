package aurocosh.divinefavor.common.lib.int_interval

import java.util.ArrayList

class IntInterval(val start: Int, stop: Int) {
    val stop: Int = Math.max(start, stop)

    fun isInsideInclusive(value: Int): Boolean {
        return value in start..stop
    }

    companion object {

        fun optimize(intervals: List<IntInterval>): List<IntInterval> {
            if (intervals.size <= 1)
                return intervals
            intervals.sortedWith(IntIntervalComparator())
            val optimal = ArrayList<IntInterval>(intervals.size)
            var previousInterval = intervals[0]
            optimal.add(previousInterval)
            for (i in 1 until intervals.size) {
                val interval = intervals[i]
                if (interval.start < previousInterval.stop)
                    previousInterval = IntInterval(previousInterval.stop + 1, interval.stop)
                else
                    previousInterval = interval
                optimal.add(previousInterval)
            }

            return optimal
        }
    }
}
