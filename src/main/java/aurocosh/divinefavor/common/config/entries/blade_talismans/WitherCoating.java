package aurocosh.divinefavor.common.config.entries.blade_talismans;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class WitherCoating {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Withering time")
    public int witheringTime = UtilTick.INSTANCE.secondsToTicks(10);
    @Config.Name("Withering power")
    public int witheringPower = 1;
}
