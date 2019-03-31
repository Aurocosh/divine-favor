package aurocosh.divinefavor.common.custom_data.player.data.aura.energetic;

import aurocosh.divinefavor.common.config.common.ConfigAura;
import aurocosh.divinefavor.common.lib.IncrementalChanceCounter;

// The default implementation of the capability. Holds all the logic.
public class EnergeticAuraData {
    private final IncrementalChanceCounter chanceCounter;

    public EnergeticAuraData() {
        chanceCounter = new IncrementalChanceCounter(ConfigAura.energeticAura.startingChance, ConfigAura.energeticAura.chanceIncrease);
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
