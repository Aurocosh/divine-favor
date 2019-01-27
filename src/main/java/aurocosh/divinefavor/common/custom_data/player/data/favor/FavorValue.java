package aurocosh.divinefavor.common.custom_data.player.data.favor;

import net.minecraft.util.math.MathHelper;

public class FavorValue {
    private int value;
    private int regen;
    private int minLimit;
    private int maxLimit;

    public FavorValue() {
        value = 0;
        regen = 100;
        minLimit = 0;
        maxLimit = 1000;
    }

    public FavorValue(int value, int regen, int minLimit, int maxLimit) {
        this.value = value;
        this.regen = regen;
        this.minLimit = minLimit;
        this.maxLimit = maxLimit;
    }

    public int getValue() {
        return value;
    }

    public void addValue(int value) {
        setValue(this.value + value);
    }

    public boolean consume(int value) {
        if(this.value < value)
            return false;
        setValue(this.value - value);
        return true;
    }

    public void setValue(int value) {
        this.value = MathHelper.clamp(value, minLimit, maxLimit);
    }

    public int getRegen() {
        return regen;
    }

    public void setRegen(int regen) {
        this.regen = Math.min(regen, maxLimit);
    }

    public void regenerate() {
        addValue(regen);
    }

    public int getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(int minLimit) {
        this.minLimit = minLimit;
        value = Math.max(value, minLimit);
    }

    public int getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(int maxLimit) {
        this.maxLimit = maxLimit;
        value = Math.min(value, maxLimit);
    }
}
