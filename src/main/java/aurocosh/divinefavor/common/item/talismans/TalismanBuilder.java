package aurocosh.divinefavor.common.item.talismans;

import aurocosh.divinefavor.common.spell.base.ModSpell;

public class TalismanBuilder {
    private String name;
    private ModSpell spell;
    private int startingSpellUses;
    private boolean castOnUse;
    private boolean castOnRightClick;
    private boolean isFree;

    public TalismanBuilder(String name, int startingSpellUses) {
        this.name = name;
        this.startingSpellUses = startingSpellUses;
        castOnUse = false;
        castOnRightClick = false;
    }

    public TalismanBuilder castOnUse() {
        this.castOnUse = true;
        return this;
    }

    public TalismanBuilder castOnRighClick() {
        this.castOnRightClick = true;
        return this;
    }

    public TalismanBuilder setIsFree() {
        this.isFree = true;
        return this;
    }

    public TalismanBuilder setSpell(ModSpell spell) {
        this.spell = spell;
        return this;
    }

    public ItemTalisman create() {
        return new ItemTalisman(name, startingSpellUses, spell, castOnUse, castOnRightClick, isFree);
    }
}
