package aurocosh.divinefavor.common.config.presences;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class EnergeticPresence {
    @Config.Name("Duration")
    public int duration = UtilTick.minutesToTicks(2);
    @Config.Name("Time to run on water")
    public int timeToRunOnWater = UtilTick.minutesToTicks(0.3f);
}
