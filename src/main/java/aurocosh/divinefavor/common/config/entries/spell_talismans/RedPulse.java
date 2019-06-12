package aurocosh.divinefavor.common.config.entries.spell_talismans;

import net.minecraftforge.common.config.Config;

public class RedPulse {
    @Config.Name("Favor cost")
    public int favorCost;
    @Config.Name("Light level")
    public int lightLevel;
    @Config.Name("Redstone level")
    public int redLevel;
    @Config.Name("Despawn delay")
    public int despawnDelay;

    public RedPulse(int favorCost, int lightLevel, int redLevel, int despawnDelay) {
        this.favorCost = favorCost;
        this.lightLevel = lightLevel;
        this.redLevel = redLevel;
        this.despawnDelay = despawnDelay;
    }
}
