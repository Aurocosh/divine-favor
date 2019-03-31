package aurocosh.divinefavor.common.custom_data.player.data.aura.hunters;

import aurocosh.divinefavor.common.config.common.ConfigAura;
import aurocosh.divinefavor.common.lib.IncrementalChanceCounter;

// The default implementation of the capability. Holds all the logic.
public class HuntersAuraData {
    private final IncrementalChanceCounter chanceCounter;

    public HuntersAuraData() {
        chanceCounter = new IncrementalChanceCounter(ConfigAura.huntersAura.startingChance, ConfigAura.huntersAura.chanceIncrease);
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
