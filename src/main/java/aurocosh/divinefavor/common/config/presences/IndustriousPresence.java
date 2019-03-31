package aurocosh.divinefavor.common.config.presences;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class IndustriousPresence {
    @Config.Name("Duration")
    public int duration = UtilTick.minutesToTicks(2);
    @Config.Name("Ore to break")
    public int oreToBreak = 10;
}
