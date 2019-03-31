package aurocosh.divinefavor.common.custom_data.player.data.presence.scorching;

import aurocosh.divinefavor.common.config.common.ConfigPresence;
import aurocosh.divinefavor.common.lib.IncrementalChanceCounter;

public class ScorchingPresenceData {
    private IncrementalChanceCounter chanceCounter;

    public ScorchingPresenceData() {
        chanceCounter = new IncrementalChanceCounter(ConfigPresence.scorchingPresence.startingChance, ConfigPresence.scorchingPresence.chanceIncrease);
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
