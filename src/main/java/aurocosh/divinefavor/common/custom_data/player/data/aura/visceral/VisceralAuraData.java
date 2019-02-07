package aurocosh.divinefavor.common.custom_data.player.data.aura.visceral;

import aurocosh.divinefavor.common.lib.IncrementalChanceCounter;

// The default implementation of the capability. Holds all the logic.
public class VisceralAuraData {
    private static final float STARTING_CHANCE = 0.0f;
    private static final float CHANCE_INCREASE = 0.05f;

    private final IncrementalChanceCounter chanceCounter;

    public VisceralAuraData() {
        chanceCounter = new IncrementalChanceCounter(STARTING_CHANCE, CHANCE_INCREASE);
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
