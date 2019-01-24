package aurocosh.divinefavor.common.custom_data.living.data.cripple;

import aurocosh.divinefavor.common.lib.SimpleCounter;
import aurocosh.divinefavor.common.util.UtilTick;

public class CrippleData {
    public static final int CURE_TIME = UtilTick.secondsToTicks(10);

    private SimpleCounter counter;

    public CrippleData() {
        this.counter = new SimpleCounter(CURE_TIME);
    }

    public void resetCureTimer() {
        counter.reset();
    }

    public boolean cureTick() {
        return counter.count();
    }

    public int getCureTicks() {
        return counter.getCount();
    }

    public void setCureTicks(int ticks) {
        counter.setCount(ticks);
    }
}
