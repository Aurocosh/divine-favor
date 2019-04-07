package aurocosh.divinefavor.common.custom_data.player.data.favor;

import aurocosh.divinefavor.common.item.contract.ItemContract;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.util.math.MathHelper;

public class SpiritStatus {
    private final ModSpirit spirit;
    private int regen;
    private int minLimit;
    private int maxLimit;

    public SpiritStatus(ModSpirit spirit) {
        this.spirit = spirit;
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
        regen = spirit.getFavorRegen();
        minLimit = spirit.getFavorMin();
        maxLimit = spirit.getFavorMax();
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
