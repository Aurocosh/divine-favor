package aurocosh.divinefavor.common.config.entries.presences;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class IndustriousPresence {
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.minutesToTicks(2);
    @Config.Name("Ore to break")
    public int oreToBreak = 10;
}
