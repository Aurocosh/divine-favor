package aurocosh.divinefavor.common.favor_sources.conditions;

import aurocosh.divinefavor.common.favor_sources.conditions.base.FavorCondition;
import aurocosh.divinefavor.common.lib.TimePeriod;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.CheckForNull;

public class TimeOfDayCondition extends FavorCondition {
    private final TimePeriod timePeriod;

    public TimeOfDayCondition(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }

    @Override
    public boolean isMet(EntityPlayer player, @CheckForNull Object context) {
        int time = (int) (player.world.getWorldTime() % 24000);
        return timePeriod.isDayTimeInRange(time);
    }
}
