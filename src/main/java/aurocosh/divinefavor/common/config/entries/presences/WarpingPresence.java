package aurocosh.divinefavor.common.config.entries.presences;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class WarpingPresence {
    @Config.Name("Duration")
    public int duration = UtilTick.minutesToTicks(2);
    @Config.Name("Starting chance")
    public float startingChance = 0.15f;
    @Config.Name("Chance increase")
    public float chanceIncrease = 0.20f;
}
