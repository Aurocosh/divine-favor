package aurocosh.divinefavor.common.custom_data.player.data.favor;

import aurocosh.divinefavor.common.item.contract.ItemContract;
import net.minecraft.util.math.MathHelper;

public class FavorStatus {
    private int regen;
    private int minLimit;
    private int maxLimit;

    public FavorStatus() {
        regen = 0;
        minLimit = 0;
        maxLimit = 0;
    }

    public int getRegen() {
        return regen;
    }

    public int getMinLimit() {
        return minLimit;
    }

    public int getMaxLimit() {
        return maxLimit;
    }

    public void clear() {
        regen = 0;
        minLimit = 0;
        maxLimit = 0;
    }

    public void addStats(ItemContract contract) {
        regen += contract.getRegen();
        minLimit += contract.getMin();
        maxLimit += contract.getMax();
    }

    public int clamp(int value) {
        return MathHelper.clamp(value, minLimit, maxLimit);
    }
}
