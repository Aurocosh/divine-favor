package aurocosh.divinefavor.common.custom_data.living.petrification;

import aurocosh.divinefavor.common.lib.SimpleCounter;
import aurocosh.divinefavor.common.lib.TickCounter;
import aurocosh.divinefavor.common.util.UtilTick;

// The default implementation of the capability. Holds all the logic.
public class DefaultPetrificationHandler implements IPetrificationHandler {
    public static final int CURE_TIME = UtilTick.secondsToTicks(6);
    public static final int DAMAGE_TIME = UtilTick.secondsToTicks(2);

    private final SimpleCounter cureCounter;
    private final TickCounter damageCounter;

    public DefaultPetrificationHandler() {
        cureCounter = new SimpleCounter(CURE_TIME);
        damageCounter = new TickCounter(DAMAGE_TIME);
    }

    @Override
    public void resetCureTimer() {
        cureCounter.reset();
    }

    @Override
    public boolean cureTick() {
        return cureCounter.count();
    }

    @Override
    public boolean damageTick() {
        return damageCounter.tick();
    }

    @Override
    public int getCureTicks() {
        return cureCounter.getCount();
    }

    @Override
    public void setCureTicks(int ticks) {
        cureCounter.setCount(ticks);
    }
}
