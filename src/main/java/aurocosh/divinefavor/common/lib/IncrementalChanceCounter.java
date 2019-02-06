package aurocosh.divinefavor.common.lib;

import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.util.math.MathHelper;

public class IncrementalChanceCounter {
    private float chance;
    private float chanceIncrease;
    private float startingChance;

    public IncrementalChanceCounter(float startingChance, float chanceIncrease) {
        this.startingChance = startingChance;
        this.chanceIncrease = chanceIncrease;
        reset();
    }

    public float getChance() {
        return chance;
    }

    public float getChanceIncrease() {
        return chanceIncrease;
    }

    public float getStartingChance() {
        return startingChance;
    }

    public void setChance(float chance) {
        this.chance = MathHelper.clamp(chance, 0, 1);
    }

    public void setChanceIncrease(float chanceIncrease) {
        this.chanceIncrease = MathHelper.clamp(chanceIncrease, 0, 1);
    }

    public void setStartingChance(float startingChance) {
        this.startingChance = MathHelper.clamp(startingChance, 0, 1);
    }

    public void reset() {
        chance = startingChance;
    }

    public boolean tryLuck() {
        System.out.println(toString());
        if (UtilRandom.rollDiceFloat(chance))
            return true;
        if (chance >= 1)
            return true;
        chance += chanceIncrease;
        return false;
    }

    @Override
    public String toString() {
        return "IncrementalChanceCounter{" +
                "chance=" + chance +
                ", chanceIncrease=" + chanceIncrease +
                ", startingChance=" + startingChance +
                '}';
    }
}
