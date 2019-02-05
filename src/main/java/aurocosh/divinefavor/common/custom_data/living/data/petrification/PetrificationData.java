package aurocosh.divinefavor.common.custom_data.living.data.petrification;

import aurocosh.divinefavor.common.lib.SimpleCounter;
import aurocosh.divinefavor.common.lib.LoopedCounter;
import aurocosh.divinefavor.common.util.UtilTick;

public class PetrificationData {
    public static final int CURE_TIME = UtilTick.secondsToTicks(6);
    public static final int DAMAGE_TIME = UtilTick.secondsToTicks(2);

    private final SimpleCounter cureCounter;
    private final LoopedCounter damageCounter;

    public PetrificationData() {
        cureCounter = new SimpleCounter(CURE_TIME);
        damageCounter = new LoopedCounter(DAMAGE_TIME);
    }

    public void resetCureTimer() {
        cureCounter.reset();
    }

    public boolean cureTick() {
        return cureCounter.count();
    }

    public boolean damageTick() {
        return damageCounter.tick();
    }

    public int getCureTicks() {
        return cureCounter.getCount();
    }

    public void setCureTicks(int ticks) {
        cureCounter.setCount(ticks);
    }
}
