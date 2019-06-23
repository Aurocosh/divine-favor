package aurocosh.divinefavor.common.config.entries.curses;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class LimpLeg {
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.minutesToTicks(1);
}
