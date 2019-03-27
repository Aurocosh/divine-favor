package aurocosh.divinefavor.common.config.talismans.arrow;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class Petrification {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Duration")
    public int duration = UtilTick.secondsToTicks(40);
    @Config.Name("Damage")
    public float damage =  4f;
    @Config.Name("Damage rate")
    public int damageRate = UtilTick.secondsToTicks(6);
    @Config.Name("Cure time")
    public int cureRate = UtilTick.secondsToTicks(2);
}
