package aurocosh.divinefavor.common.custom_data.player.data.presence.warping;

import aurocosh.divinefavor.common.config.common.ConfigPresence;
import aurocosh.divinefavor.common.lib.IncrementalChanceCounter;

public class WarpingPresenceData {
    private IncrementalChanceCounter chanceCounter;

    public WarpingPresenceData() {
        chanceCounter = new IncrementalChanceCounter(ConfigPresence.warpingPresence.startingChance, ConfigPresence.warpingPresence.chanceIncrease);
    }

    public void reset() {
        chanceCounter.reset();
    }

    public boolean tryLuck() {
        return chanceCounter.tryLuck();
    }

    public float getChance() {
        return chanceCounter.getChance();
    }

    public void setChance(float chance) {
        chanceCounter.setChance(chance);
    }
}
