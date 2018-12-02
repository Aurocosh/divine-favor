package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.item.talisman.ItemTalisman;
import aurocosh.divinefavor.common.requirements.base.SpellRequirement;
import aurocosh.divinefavor.common.requirements.cost.costs.Cost;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.util.helper_classes.TimePeriod;

import java.util.ArrayList;
import java.util.List;

public class SpiritBuilder {
    private String name;
    private List<TimePeriod> activityPeriods;

    public SpiritBuilder(String name) {
        this.name = name;
        activityPeriods = new ArrayList<>();
    }

    public SpiritBuilder addActivityPeriod(TimePeriod period){
        activityPeriods.add(period);
        return this;
    }

    public ModSpirit create(){
        return new ModSpirit(name,activityPeriods);
    }
}
