package aurocosh.divinefavor.common.config.entries.curses;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class Skyfall {
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.secondsToTicks(8);
    @Config.Name("Slowness force")
    public float slownessForce =  4f;
}
