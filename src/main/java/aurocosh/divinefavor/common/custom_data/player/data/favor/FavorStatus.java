package aurocosh.divinefavor.common.custom_data.player.data.favor;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.contract.ItemContract;
import net.minecraft.util.math.MathHelper;

public class FavorStatus {
    private final ModFavor favor;
    private int regen;
    private int minLimit;
    private int maxLimit;

    public FavorStatus(ModFavor favor) {
        this.favor = favor;
        reset();
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

    public void reset() {
        regen = favor.getFavorRegen();
        minLimit = favor.getFavorMin();
        maxLimit = favor.getFavorMax();
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
