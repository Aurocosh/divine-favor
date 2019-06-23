package aurocosh.divinefavor.common.config.entries.curses;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class SuffocatingFumes {
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.minutesToTicks(2);
    @Config.Name("Height to climb")
    public int heightToClimb = 15;
    @Config.Name("Damage")
    public float damage = 2;
    @Config.Name("Damage rate")
    public int damageRate = UtilTick.INSTANCE.secondsToTicks(3);
    @Config.Name("Cure rate")
    public int cureRate = UtilTick.INSTANCE.secondsToTicks(1);
}
