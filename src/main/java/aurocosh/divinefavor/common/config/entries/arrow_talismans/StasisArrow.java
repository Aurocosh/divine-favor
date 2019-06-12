package aurocosh.divinefavor.common.config.entries.arrow_talismans;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class StasisArrow {
    @Config.Name("Favor cost")
    public int favorCost = 80;
    @Config.Name("Damage")
    public float damage = 2;
    @Config.Name("Radius")
    public int radius = 6;
    @Config.Name("Despawn delay")
    public int despawnDelay = UtilTick.INSTANCE.secondsToTicks(60);
}
