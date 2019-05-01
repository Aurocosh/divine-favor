package aurocosh.divinefavor.common.config.entries.talismans.arrow;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class FillLungs {
    @Config.Name("Favor cost")
    public int favorCost = 100;
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.minutesToTicks(2);
    @Config.Name("drowningRate")
    public int drowningRate = UtilTick.INSTANCE.secondsToTicks(3);
    @Config.Name("Damage")
    public float damage = 20;
}
