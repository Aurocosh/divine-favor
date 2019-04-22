package aurocosh.divinefavor.common.config.entries.presences;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class ChillingPresence {
    @Config.Name("Duration")
    public int duration = UtilTick.minutesToTicks(2);
    @Config.Name("Spawn radius")
    public int spawnRadius = 7;
    @Config.Name("Min wolfs to spawn")
    public int minWolfsToSpawn = 3;
    @Config.Name("Min wolfs to spawn")
    public int maxWolfsToSpawn = 5;
}
