package aurocosh.divinefavor.common.config.talismans.arrow;

import aurocosh.divinefavor.common.config.data.TimeIntervalSeconds;
import net.minecraftforge.common.config.Config;

public class TracerArrow {
    @Config.Name("Favor cost")
    public int favorCost = 10;
    @Config.Name("Damage")
    public float damage = 0;
    @Config.Name("Particle density")
    public int particleDensity = 10;
    @Config.Name("Particle despawn interval")
    public TimeIntervalSeconds despawnInterval = new TimeIntervalSeconds(60, 120);
}
