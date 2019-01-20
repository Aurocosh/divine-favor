package aurocosh.divinefavor.common.custom_data.living.limp_leg;

import aurocosh.divinefavor.common.lib.SimpleCounter;
import aurocosh.divinefavor.common.util.UtilTick;

// The default implementation of the capability. Holds all the logic.
public class DefaultLimpLegHandler implements ILimpLegHandler {
    public static final int SQUAT_TIME = UtilTick.secondsToTicks(5);

    private SimpleCounter counter;

    public DefaultLimpLegHandler() {
        this.counter = new SimpleCounter(SQUAT_TIME);
    }

    @Override
    public void reset() {
        counter.reset();
    }

    @Override
    public boolean sneakingTick() {
        return counter.count();
    }

    @Override
    public int getCurrentTicks() {
        return counter.getCount();
    }

    @Override
    public void setCurrentTicks(int ticks) {
        counter.setCount(ticks);
    }
}
