package aurocosh.divinefavor.common.custom_data.player.data.presence.towering;

import aurocosh.divinefavor.common.lib.LoopedCounter;
import aurocosh.divinefavor.common.util.UtilRandom;
import aurocosh.divinefavor.common.util.UtilTick;

// The default implementation of the capability. Holds all the logic.
public class ToweringPresenceData {
    private static final int MIN_CURSE_DELAY = UtilTick.secondsToTicks(15);
    private static final int MAX_CURSE_DELAY = UtilTick.secondsToTicks(45);

    private final LoopedCounter curseCounter;

    public ToweringPresenceData() {
        curseCounter = new LoopedCounter(MIN_CURSE_DELAY);
    }

    public void reset() {
        curseCounter.setTickRate(MIN_CURSE_DELAY);
        curseCounter.reset();
    }

    public boolean tick() {
        if (!curseCounter.tick())
            return false;
        int nextDelay = UtilRandom.nextInt(MIN_CURSE_DELAY, MAX_CURSE_DELAY);
        curseCounter.setTickRate(nextDelay);
        return true;
    }

    public int getCurseTime() {
        return curseCounter.getCount();
    }

    public void setCurseTime(int ticks) {
        curseCounter.setCount(ticks);
    }
}
