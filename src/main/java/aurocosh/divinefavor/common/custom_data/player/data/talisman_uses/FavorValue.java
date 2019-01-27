package aurocosh.divinefavor.common.custom_data.player.data.talisman_uses;

import net.minecraft.util.math.MathHelper;

public class FavorValue {
    private int value;
    private int minLimit;
    private int maxLimit;

    public FavorValue() {
        value = 0;
        minLimit = 0;
        maxLimit = 1000;
    }

    public FavorValue(int value, int minLimit, int maxLimit) {
        this.value = value;
        this.minLimit = minLimit;
        this.maxLimit = maxLimit;
    }

    public int getFavor() {
        return value;
    }

    public void setFavor(int value) {
        this.value = MathHelper.clamp(value, minLimit, maxLimit);
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
