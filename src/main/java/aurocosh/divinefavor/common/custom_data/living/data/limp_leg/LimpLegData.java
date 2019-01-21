package aurocosh.divinefavor.common.custom_data.living.data.limp_leg;

import aurocosh.divinefavor.common.lib.SimpleCounter;
import aurocosh.divinefavor.common.util.UtilTick;

public class LimpLegData {
    public static final int SQUAT_TIME = UtilTick.secondsToTicks(5);

    private SimpleCounter counter;

    public LimpLegData() {
        this.counter = new SimpleCounter(SQUAT_TIME);
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
