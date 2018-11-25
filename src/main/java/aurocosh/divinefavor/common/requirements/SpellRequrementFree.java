package aurocosh.divinefavor.common.requirements;

import aurocosh.divinefavor.common.lib.LibSpellRequirementNames;
import aurocosh.divinefavor.common.requirements.base.SpellRequirement;
import aurocosh.divinefavor.common.lib.LibFavorType;
import aurocosh.divinefavor.common.spell.base.SpellContext;

public class SpellRequrementFree extends SpellRequirement {
    public SpellRequrementFree()
    {
        super(LibSpellRequirementNames.FREE, LibFavorType.NOT_SET, false);
    }

    @Override
    protected boolean claim(SpellContext context) {
        return true;
    }

    @Override
    public String toString()
    {
        return "Free";
    }
}
