package aurocosh.divinefavor.common.config.punishment;

import aurocosh.divinefavor.common.config.IntervalConfig;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class BlizrabiPunishmentConfig {
    @Config.Name("Wolfs to spawn")
    public IntervalConfig wolfsToSpawn = new IntervalConfig(9, 12);
    @Config.Name("Spawn radius")
    public int spawnRadius = 10;

    @Config.Name("Slowness duration")
    public int slownessDuration = UtilTick.minutesToTicks(3);
    @Config.Name("Slowness amplifier")
    public int slownessAmplifier = 3;

    @Config.Name("Weakness duration")
    public int weaknessDuration = UtilTick.minutesToTicks(3);
    @Config.Name("Weakness amplifier")
    public int weaknessAmplifier = 3;
}
