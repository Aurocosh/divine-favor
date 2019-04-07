package aurocosh.divinefavor.common.config.talismans.arrow;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class Cripple {
    @Config.Name("Favor cost")
    public int favorCost = 100;
    @Config.Name("Duration")
    public int duration = UtilTick.minutesToTicks(2);
    @Config.Name("Slowness force")
    public float slownessForce =  0.2f;
    @Config.Name("Cure time")
    public int cureRate = UtilTick.secondsToTicks(10);
}
