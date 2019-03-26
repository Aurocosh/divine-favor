package aurocosh.divinefavor.common.config.talismans.spell;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class StoneFever {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Duration")
    public int duration = UtilTick.minutesToTicks(5);
    @Config.Name("Slowness duration")
    public int slownessDuration = UtilTick.secondsToTicks(10);
    @Config.Name("Blindness duration")
    public int blindnessDuration = UtilTick.secondsToTicks(2);
    @Config.Name("Fatigue duration")
    public int fatigueDuration = UtilTick.minutesToTicks(3);
    @Config.Name("Fatigue level")
    public int fatigueLevel = 5;
}
