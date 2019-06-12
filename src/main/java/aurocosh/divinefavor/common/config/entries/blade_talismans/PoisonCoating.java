package aurocosh.divinefavor.common.config.entries.blade_talismans;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class PoisonCoating {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Poison time")
    public int poisonTime = UtilTick.INSTANCE.secondsToTicks(10);
    @Config.Name("Poison power")
    public int poisonPower = 1;
}
