package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.lib.TimePeriod;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment;
import aurocosh.divinefavor.common.util.UtilDayTime;

import java.util.ArrayList;
import java.util.List;

public class SpiritBuilder {
    private String name;
    private List<TimePeriod> activityPeriods;
    private List<ItemSpellTalisman> talismans;
    private SpiritPunishment punishment;

    public SpiritBuilder(String name) {
        this.name = name;
        activityPeriods = new ArrayList<>();
        talismans = new ArrayList<>();
        punishment = new SpiritPunishment();
    }

    public SpiritBuilder addActivityPeriod(int start, int stop){
        activityPeriods.add(UtilDayTime.fromHours(start, stop));
        return this;
    }

    public SpiritBuilder addTalisman(ItemSpellTalisman talisman){
        talismans.add(talisman);
        return this;
    }

    public SpiritBuilder setPunishment(SpiritPunishment punishment){
        this.punishment = punishment;
        return this;
    }

    public ModSpirit create(){
        return new ModSpirit(name,activityPeriods, talismans, punishment);
    }
}
