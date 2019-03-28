package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.config.TimePeriodConfig;
import aurocosh.divinefavor.common.favor.ModFavor;
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
    private TimePeriod activityPeriod;
    private SpiritPunishment punishment;

    public SpiritBuilder(String name, TimePeriodConfig timePeriod) {
        this.name = name;
        favors = new ArrayList<>();
        punishment = new SpiritPunishment();
        this.activityPeriod = UtilDayTime.fromHours(timePeriod.start, timePeriod.stop);
    }

    public SpiritBuilder addFavor(ModFavor favor) {
        favors.add(favor);
        return this;
    }

    public SpiritBuilder setPunishment(SpiritPunishment punishment) {
        this.punishment = punishment;
        return this;
    }

    public ModSpirit create() {
        return new ModSpirit(name, favors, activityPeriod, Collections.singletonList(0), punishment);
    }
}
