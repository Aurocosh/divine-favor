package aurocosh.divinefavor.common.favor_sources.favor_sources.regen;

import aurocosh.divinefavor.common.favor_sources.favor_sources.base.RegenFavorSource;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.util.helper_classes.TimePeriod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class TimeOfDayFavorSource extends RegenFavorSource {
    private final TimePeriod timePeriod;

    public TimeOfDayFavorSource(ModFavor favor, int favorCount, ResourceLocation unlockAdvancements, ResourceLocation lockAdvancement, int tickRate, int upperRegenLimit, TimePeriod timePeriod) {
        super(favor, favorCount, unlockAdvancements, lockAdvancement, tickRate, upperRegenLimit);
        this.timePeriod = timePeriod;
    }

    @Override
    public boolean IsFavorNeeded(EntityPlayer player) {
        if(!super.IsFavorNeeded(player))
            return false;
        int dayTime = (int) (player.world.getWorldTime() % 24000);
        return timePeriod.isDayTimeInRange(dayTime);
    }
}
