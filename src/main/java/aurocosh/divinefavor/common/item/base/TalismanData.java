package aurocosh.divinefavor.common.item.base;

import aurocosh.divinefavor.common.requirements.base.SpellRequirement;
import aurocosh.divinefavor.common.spell.base.SpellType;
import com.google.gson.annotations.Expose;

public class TalismanData {
    @Expose
    public String name;
    @Expose
    public SpellType spellType;
    @Expose
    public SpellRequirement requirement;
    @Expose
    public boolean castOnUse;
    @Expose
    public boolean castOnRightClick;

    public TalismanData(String name, SpellType spellType, SpellRequirement requirement, boolean castOnUse, boolean castOnRightClick) {
        this.name = name;
        this.spellType = spellType;
        this.requirement = requirement;
        this.castOnUse = castOnUse;
        this.castOnRightClick = castOnRightClick;
    }
}
