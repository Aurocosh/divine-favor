package aurocosh.divinefavor.common.requirements.base;

import aurocosh.divinefavor.common.constants.LibSpellRequirementNames;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import com.google.gson.JsonObject;

public class SpellRequirementFree extends SpellRequirement {
    public SpellRequirementFree()
    {
        super(LibSpellRequirementNames.FREE);
    }

    @Override
    public boolean canClaimCostInternal(SpellContext context) {
        return true;
    }

    @Override
    public boolean claimCostInternal(SpellContext context) {
        return true;
    }

    @Override
    public String toString()
    {
        return "Free";
    }

    public static SpellRequirementFree deserialize(JsonObject json)
    {
        return new SpellRequirementFree();
    }
}
