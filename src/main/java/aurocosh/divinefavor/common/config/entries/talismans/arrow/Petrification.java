package aurocosh.divinefavor.common.config.entries.talismans.arrow;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class Petrification {
    @Config.Name("Favor cost")
    public int favorCost = 100;
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.secondsToTicks(40);
    @Config.Name("Damage")
    public float damage =  4f;
    @Config.Name("Damage rate")
    public int damageRate = UtilTick.INSTANCE.secondsToTicks(6);
    @Config.Name("Cure time")
    public int cureRate = UtilTick.INSTANCE.secondsToTicks(2);
}
