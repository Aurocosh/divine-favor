package aurocosh.divinefavor.common.custom_data.player.data.presence.towering;

import aurocosh.divinefavor.common.config.common.ConfigPresence;
import aurocosh.divinefavor.common.lib.LoopedCounter;
import aurocosh.divinefavor.common.util.UtilRandom;
import aurocosh.divinefavor.common.util.UtilTick;

public class ToweringPresenceData {
    private final LoopedCounter curseCounter;

    public ToweringPresenceData() {
        curseCounter = new LoopedCounter(ConfigPresence.toweringPresence.minCurseDelay);
    }

    public void reset() {
        curseCounter.setTickRate(ConfigPresence.toweringPresence.minCurseDelay);
        curseCounter.reset();
    }

    public boolean tick() {
        if (!curseCounter.tick())
            return false;
        int nextDelay = UtilRandom.nextInt(ConfigPresence.toweringPresence.minCurseDelay, ConfigPresence.toweringPresence.maxCurseDelay);
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
