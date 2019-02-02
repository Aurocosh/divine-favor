package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.lib.TimePeriod;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment;
import aurocosh.divinefavor.common.util.UtilDayTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpiritBuilder {
    private String name;
    private List<ModFavor> favors;
    private List<TimePeriod> activityPeriods;
    private SpiritPunishment punishment;

    public SpiritBuilder(String name) {
        this.name = name;
        favors = new ArrayList<>();
        activityPeriods = new ArrayList<>();
        punishment = new SpiritPunishment();
    }

    public SpiritBuilder addFavor(ModFavor favor) {
        favors.add(favor);
        return this;
    }

    public SpiritBuilder addActivityPeriod(int start, int stop) {
        activityPeriods.add(UtilDayTime.fromHours(start, stop));
        return this;
    }

    public SpiritBuilder setPunishment(SpiritPunishment punishment) {
        this.punishment = punishment;
        return this;
    }

    public ModSpirit create() {
        return new ModSpirit(name, favors, activityPeriods, Collections.singletonList(0), punishment);
    }
}
