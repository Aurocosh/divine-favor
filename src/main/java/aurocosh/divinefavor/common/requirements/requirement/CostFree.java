package aurocosh.divinefavor.common.requirements.requirement;

import aurocosh.divinefavor.common.spell.base.SpellContext;

public class CostFree extends Cost {
    public CostFree(int priority)
    {
        super(priority);
    }

    @Override
    public boolean canClaim(SpellContext context)
    {
        return true;
    }

    @Override
    public boolean claim(SpellContext context)
    {
        return true;
    }

    @Override
    public String getUsageInfo(SpellContext context) {
        return "Unlimited use";
    }
}