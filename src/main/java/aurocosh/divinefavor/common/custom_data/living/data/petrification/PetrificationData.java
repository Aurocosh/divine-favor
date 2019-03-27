package aurocosh.divinefavor.common.custom_data.living.data.petrification;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.lib.LoopedCounter;
import aurocosh.divinefavor.common.lib.SimpleCounter;

public class PetrificationData {
    private final SimpleCounter cureCounter;
    private final LoopedCounter damageCounter;

    public PetrificationData() {
        cureCounter = new SimpleCounter(ConfigArrow.petrification.cureRate);
        damageCounter = new LoopedCounter(ConfigArrow.petrification.damageRate);
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
