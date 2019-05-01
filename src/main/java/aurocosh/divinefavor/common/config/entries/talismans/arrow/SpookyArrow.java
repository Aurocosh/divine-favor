package aurocosh.divinefavor.common.config.entries.talismans.arrow;

import aurocosh.divinefavor.common.config.data.TimeIntervalSeconds;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class SpookyArrow {
    @Config.Name("Favor cost")
    public int favorCost = 30;
    @Config.Name("Damage")
    public float damage = 2;
    @Config.Name("Despawn delay")
    public int despawnDelay = UtilTick.INSTANCE.minutesToTicks(3);
    @Config.Name("Sound delay")
    public TimeIntervalSeconds soundDelay = new TimeIntervalSeconds(3,15);
}
