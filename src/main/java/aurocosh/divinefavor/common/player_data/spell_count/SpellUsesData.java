package aurocosh.divinefavor.common.player_data.spell_count;

import aurocosh.divinefavor.common.util.UtilMath;

public class SpellUsesData {
    private int maxSpellUses;
    private int spellUses;

    public SpellUsesData() {
        maxSpellUses = 1;
        spellUses = 0;
    }

    public SpellUsesData(int maxSpellUses, int spellUses) {
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
