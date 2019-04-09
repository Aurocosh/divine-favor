package aurocosh.divinefavor.common.config.punishment;

import aurocosh.divinefavor.common.config.data.IntInterval;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

import java.util.HashMap;
import java.util.Map;

public class LoonPunishmentConfig {
    @Config.Name("Enemies per wave")
    public IntInterval enemiesPerWave = new IntInterval(2, 5);
    @Config.Name("Wave count")
    public IntInterval waveCount = new IntInterval(2, 5);
    @Config.Name("Wave delay")
    public IntInterval waveDelay = new IntInterval(UtilTick.secondsToTicks(15), UtilTick.minutesToTicks(2));

    @Config.Name("Spawn radius")
    public int spawnRadius = 10;

    @Config.Name("Summoned enemies")
    public Map<String, Double> summonedEnemies = new HashMap<String, Double>() {
        {
            put("minecraft:skeleton", 0.2d);
            put("minecraft:zombie", 0.3d);
            put("minecraft:husk", 0.1d);
            put("minecraft:stray", 0.05d);
            put("minecraft:spider", 0.2d);
            put("minecraft:creeper", 0.1d);
            put("minecraft:cave_spider", 0.05d);
        }
    };
}
