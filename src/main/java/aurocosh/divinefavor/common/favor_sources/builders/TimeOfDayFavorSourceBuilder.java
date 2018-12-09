package aurocosh.divinefavor.common.favor_sources.builders;

import aurocosh.divinefavor.common.favor_sources.favor_sources.base.FavorSource;
import aurocosh.divinefavor.common.favor_sources.favor_sources.regen.TimeOfDayFavorSource;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.util.helper_classes.TimePeriod;

public class TimeOfDayFavorSourceBuilder extends RegenFavorSourceBuilder<TimeOfDayFavorSourceBuilder> {
    private TimePeriod timePeriod;

    public TimeOfDayFavorSourceBuilder(ModFavor favor, int favorCount) {
        super(favor, favorCount);
        timePeriod = new TimePeriod(0, 1000);
    }

    public TimeOfDayFavorSourceBuilder setPeriod(int start, int stop) {
        this.timePeriod = TimePeriod.fromHours(start,stop);
        return this;
    }

    @Override
    public FavorSource create() {
        return new TimeOfDayFavorSource(favor, favorCount, unlockAdvancements, lockAdvancement, tickRate, upperRegenLimit, timePeriod);
    }
}
