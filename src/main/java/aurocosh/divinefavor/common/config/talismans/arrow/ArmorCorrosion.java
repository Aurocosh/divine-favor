package aurocosh.divinefavor.common.config.talismans.arrow;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class ArmorCorrosion {
    @Config.Name("Favor cost")
    public int favorCost = 100;
    @Config.Name("Duration")
    public int duration = UtilTick.minutesToTicks(3);
    @Config.Name("Corrosion rate")
    public int corrosionRate = UtilTick.secondsToTicks(1);
    @Config.Name("Corrosion damage")
    public int corrosionDamage = 1;
    @Config.Name("Min slots to corrode")
    public int minSlotsToCorrode = 1;
    @Config.Name("Max slots to corrode")
    public int maxSlotsToCorrode = 3;
}
