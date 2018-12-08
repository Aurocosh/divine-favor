package aurocosh.divinefavor.common.requirements.cost.costs;

import aurocosh.divinefavor.common.requirements.cost.CostType;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import com.google.gson.annotations.Expose;

public class CostDayTime extends Cost {
    @Expose
    private DayTimeEnum dayTime;

    @Override
    public void init() {
        type = CostType.DAY_TIME;
    }

    public CostDayTime(DayTimeEnum dayTime) {
        this.dayTime = dayTime;
    }

    @Override
    public boolean canClaim(SpellContext context)
    {
        return isItTime(context);
    }

    @Override
    public boolean claim(SpellContext context)
    {
        return true;
    }

    @Override
    public String getUsageInfo(SpellContext context) {
        if(isItTime(context))
            return "Unlimited use";
        return "Unusable right now";
    }

    @Override
    public int getUseCount(SpellContext context) {
        return isItTime(context) ? -1 : 0;
    }

    private boolean isItTime(SpellContext context)
    {
        int time = (int) (context.world.getWorldTime() % 24000);
        return dayTime.isTimeInPeriod(time);
    }
}