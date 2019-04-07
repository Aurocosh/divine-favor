package aurocosh.divinefavor.common.custom_data.player.data.favor;

import aurocosh.divinefavor.common.item.contract.ItemContract;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.util.math.MathHelper;

public class SpiritStatus {
    private final ModSpirit spirit;
    private int regen;
    private int minLimit;
    private int maxLimit;
    private boolean informActivity;

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

    public boolean isInformActivity() {
        return informActivity;
    }

    public void reset() {
        regen = spirit.getFavorRegen();
        minLimit = spirit.getFavorMin();
        maxLimit = spirit.getFavorMax();
        informActivity = false;
    }

    public void addStats(ItemContract contract) {
        regen += contract.getRegen();
        minLimit += contract.getMin();
        maxLimit += contract.getMax();
        informActivity = informActivity || contract.isInformActivity();
    }

    public int clamp(int value) {
        return MathHelper.clamp(value, minLimit, maxLimit);
    }
}
