package aurocosh.divinefavor.common.config.entries.curses;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class Cripple {
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.minutesToTicks(2);
    @Config.Name("Slowness force")
    public float slownessForce =  0.2f;
    @Config.Name("Cure time")
    public int cureRate = UtilTick.INSTANCE.secondsToTicks(10);
}
