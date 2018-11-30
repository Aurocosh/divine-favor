package aurocosh.divinefavor.common.requirements.cost.costs;

import aurocosh.divinefavor.common.requirements.cost.CostType;
import aurocosh.divinefavor.common.spell.base.SpellContext;

public class CostFree extends Cost {
    @Override
    public void init() {
        type = CostType.FREE;
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

    @Override
    public int getUseCount(SpellContext context) {
        return -1;
    }
}