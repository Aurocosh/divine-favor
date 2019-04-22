package aurocosh.divinefavor.common.config.entries.presences;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class ToweringPresence {
    @Config.Name("Duration")
    public int duration = UtilTick.minutesToTicks(2);
    @Config.Name("Min curse time")
    public int minCurseTime = UtilTick.secondsToTicks(40);
    @Config.Name("Max curse time")
    public int maxCurseTime = UtilTick.secondsToTicks(70);
    @Config.Name("Min curse delay")
    public int minCurseDelay = UtilTick.secondsToTicks(15);
    @Config.Name("Max curse delay")
    public int maxCurseDelay = UtilTick.secondsToTicks(45);
}
