package aurocosh.divinefavor.common.config.talismans.arrow;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class SuffocatingFumes {
    @Config.Name("Favor cost")
    public int favorCost = 100;
    @Config.Name("Duration")
    public int duration = UtilTick.minutesToTicks(2);
    @Config.Name("Height to climb")
    public int heightToClimb = 15;
    @Config.Name("Damage")
    public float damage = 2;
    @Config.Name("Damage rate")
    public int damageRate = UtilTick.secondsToTicks(3);
    @Config.Name("Cure rate")
    public int cureRate = UtilTick.secondsToTicks(1);
}
