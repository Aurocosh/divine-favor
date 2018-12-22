package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.item.talismans.ItemTalisman;
import aurocosh.divinefavor.common.lib.TimePeriod;

import java.util.ArrayList;
import java.util.List;

public class SpiritBuilder {
    private String name;
    private List<TimePeriod> activityPeriods;
    private List<ItemTalisman> talismans;

    public SpiritBuilder(String name) {
        this.name = name;
        activityPeriods = new ArrayList<>();
        talismans = new ArrayList<>();
    }

    public SpiritBuilder addActivityPeriod(int start, int stop){
        activityPeriods.add(TimePeriod.fromHours(start, stop));
        return this;
    }

    public SpiritBuilder addTalisman(ItemTalisman talisman){
        talismans.add(talisman);
        return this;
    }

    public ModSpirit create(){
        return new ModSpirit(name,activityPeriods, talismans);
    }
}
