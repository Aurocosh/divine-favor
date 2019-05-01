package aurocosh.divinefavor.common.config.entries.talismans.arrow;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class WindLeash {
    @Config.Name("Favor cost")
    public int favorCost = 80;
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.secondsToTicks(12);
    @Config.Name("Motion speed")
    public float motionSpeed = 0.7f;
    @Config.Name("Player multiplier")
    public float playerMultiplier = 1;
    @Config.Name("Tolerance")
    public float tolerance = 0.9f;
}
