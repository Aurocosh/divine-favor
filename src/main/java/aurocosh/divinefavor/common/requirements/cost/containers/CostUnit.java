package aurocosh.divinefavor.common.requirements.cost.containers;

import aurocosh.divinefavor.common.lib.IInitiatable;
import aurocosh.divinefavor.common.requirements.cost.costs.Cost;
import aurocosh.divinefavor.common.requirements.cost.costs.CostFree;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import com.google.gson.annotations.Expose;

import java.util.*;

public class CostUnit implements IInitiatable {
    @Expose
    private int priority;
    @Expose
    private List<Cost> costs;

    public int getPriority() {
        return priority;
    }

    public List<Cost> getCosts() {
        return Collections.unmodifiableList(costs);
    }

    public CostUnit(int priority, Cost cost) {
        this.priority = priority;
        costs = new ArrayList<>();
        costs.add(cost);
    }

    public CostUnit(int priority, List<Cost> costs) {
        this.priority = priority;
        this.costs = costs;
    }

    @Override
    public void init() {
        if(costs == null)
            costs = Arrays.asList(new CostFree());
        costs.forEach(Cost::init);
    }

    public boolean canClaimAllCosts(SpellContext context) {
        for (Cost cost: costs)
            if (!cost.canClaim(context))
                return false;
        return true;
    }

    public boolean claimAllCosts(SpellContext context) {
        for (Cost cost: costs)
            if (!cost.claim(context))
                return false;
        return true;
    }

    public String getUsageInfo(SpellContext context) {
        if(costs.size() == 1)
            return costs.get(0).getUsageInfo(context);

        int useCount = Integer.MAX_VALUE;
        boolean hasInfinite = false;
        for (Cost cost: costs) {
            int costUseCount = cost.getUseCount(context);
            hasInfinite = hasInfinite || costUseCount == -1;
            costUseCount = Integer.max(costUseCount,0);
            useCount = Integer.min(useCount, costUseCount);
        }

        if(useCount > 0)
            return "Uses left: " + useCount;
        if(hasInfinite)
            return "Unlimited use";
        return "Unusable";
    }

    public static class CostUnitComparator implements Comparator<CostUnit> {
        @Override
        public int compare(CostUnit o1, CostUnit o2) {
            if(o1.getPriority() == o2.getPriority())
                return 0;
            return o1.getPriority() > o2.getPriority() ? -1 : 1;
        }
    }
}