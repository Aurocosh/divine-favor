package aurocosh.divinefavor.common.item.talismans;

import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.spell.base.ModSpell;

public class TalismanBuilder {
    private String name;
    private ModSpell spell;
    private ModFavor favor;
    private int favorCost;
    private boolean castOnUse;
    private boolean castOnRightClick;

    public TalismanBuilder(String name, ModFavor favor, int favorCost) {
        this.name = name;
        this.favor = favor;
        this.favorCost = favorCost;
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

    public TalismanBuilder setSpell(ModSpell spell) {
        this.spell = spell;
        return this;
    }

    public Talisman create() {
        return new Talisman(name, spell, favor, favorCost, castOnUse, castOnRightClick);
    }
}
