package aurocosh.divinefavor.common.favor_sources.data.regen;

import aurocosh.divinefavor.common.favor_sources.data.base.RegenFavorSourceData;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.util.helper_classes.TimePeriod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class TimeOfDayFavorSourceData extends RegenFavorSourceData {
    private final TimePeriod timePeriod;

    public TimeOfDayFavorSourceData(ModFavor favor, int favorCount, ResourceLocation unlockAdvancements, ResourceLocation lockAdvancement, int tickRate, int upperRegenLimit, TimePeriod timePeriod) {
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
