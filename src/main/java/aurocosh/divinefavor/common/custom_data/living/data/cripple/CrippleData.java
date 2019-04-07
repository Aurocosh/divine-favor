package aurocosh.divinefavor.common.custom_data.living.data.cripple;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.lib.SimpleCounter;

public class CrippleData {
    private SimpleCounter counter;

    public CrippleData() {
        this.counter = new SimpleCounter(ConfigArrow.cripple.cureRate);
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
