package aurocosh.divinefavor.common.custom_data.player.data.presence.manipulative;

import aurocosh.divinefavor.common.config.common.ConfigPresence;
import aurocosh.divinefavor.common.lib.IncrementalChanceCounter;

public class ManipulativePresenceData {
    private IncrementalChanceCounter chanceCounter;

    public ManipulativePresenceData() {
        chanceCounter = new IncrementalChanceCounter(ConfigPresence.manipulativePresence.startingChance, ConfigPresence.manipulativePresence.chanceIncrease);
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
