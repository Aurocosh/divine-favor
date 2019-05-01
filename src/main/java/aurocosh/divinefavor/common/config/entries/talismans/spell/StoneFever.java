package aurocosh.divinefavor.common.config.entries.talismans.spell;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class StoneFever {
    @Config.Name("Favor cost")
    public int favorCost = 20;
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.minutesToTicks(5);
    @Config.Name("Slowness duration")
    public int slownessDuration = UtilTick.INSTANCE.secondsToTicks(10);
    @Config.Name("Blindness duration")
    public int blindnessDuration = UtilTick.INSTANCE.secondsToTicks(2);
    @Config.Name("Fatigue duration")
    public int fatigueDuration = UtilTick.INSTANCE.minutesToTicks(3);
    @Config.Name("Fatigue level")
    public int fatigueLevel = 5;
}
