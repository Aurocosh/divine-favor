package aurocosh.divinefavor.common.custom_data.player.data.aura.visceral;

import aurocosh.divinefavor.common.config.common.ConfigAura;
import aurocosh.divinefavor.common.lib.IncrementalChanceCounter;

// The default implementation of the capability. Holds all the logic.
public class VisceralAuraData {
    private final IncrementalChanceCounter chanceCounter;

    public VisceralAuraData() {
        chanceCounter = new IncrementalChanceCounter(ConfigAura.visceralAura.startingChance, ConfigAura.visceralAura.chanceIncrease);
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
