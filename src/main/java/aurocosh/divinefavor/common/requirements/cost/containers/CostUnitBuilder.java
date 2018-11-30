package aurocosh.divinefavor.common.requirements.cost.containers;

import aurocosh.divinefavor.common.requirements.cost.costs.Cost;

import java.util.ArrayList;
import java.util.List;

public class CostUnitBuilder {
    private int priority;
    private List<Cost> costs = new ArrayList<>();

    public CostUnitBuilder() {
    }

    public CostUnitBuilder setPriority(int priority){
        this.priority = priority;
        return this;
    }

    public CostUnitBuilder addCost(Cost cost){
        costs.add(cost);
        return this;
    }

    public CostUnit create(){
        return new CostUnit(priority,costs);
    }
}

