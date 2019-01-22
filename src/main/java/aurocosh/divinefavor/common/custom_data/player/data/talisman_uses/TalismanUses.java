package aurocosh.divinefavor.common.custom_data.player.data.talisman_uses;

import aurocosh.divinefavor.common.util.UtilMath;

public class TalismanUses {
    private int maxUses;
    private int uses;

    public TalismanUses() {
        maxUses = 1;
        uses = 0;
    }

    public TalismanUses(int maxUses, int uses) {
        this.maxUses = maxUses;
        setUses(uses);
    }

    public int getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(int maxUses) {
        this.maxUses = maxUses;
        setUses(uses);
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = UtilMath.clamp(uses, 0 , maxUses);
    }
}
