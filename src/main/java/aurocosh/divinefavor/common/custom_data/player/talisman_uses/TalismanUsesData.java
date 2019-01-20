package aurocosh.divinefavor.common.custom_data.player.talisman_uses;

import aurocosh.divinefavor.common.util.UtilMath;

public class TalismanUsesData {
    private int maxSpellUses;
    private int spellUses;

    public TalismanUsesData() {
        maxSpellUses = 1;
        spellUses = 0;
    }

    public TalismanUsesData(int maxSpellUses, int spellUses) {
        this.maxSpellUses = maxSpellUses;
        setSpellUses(spellUses);
    }

    public int getMaxSpellUses() {
        return maxSpellUses;
    }

    public void setMaxSpellUses(int maxSpellUses) {
        this.maxSpellUses = maxSpellUses;
        setSpellUses(spellUses);
    }

    public int getSpellUses() {
        return spellUses;
    }

    public void setSpellUses(int spellUses) {
        this.spellUses = UtilMath.clamp(spellUses, 0 , maxSpellUses);
    }
}
