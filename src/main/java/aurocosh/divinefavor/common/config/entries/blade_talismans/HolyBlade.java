package aurocosh.divinefavor.common.config.entries.blade_talismans;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class HolyBlade {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Extra damage")
    public float damage = 10;
    @Config.Name("Fire time")
    public int fireSeconds = 3;
    @Config.Name("Slowness time")
    public int slownessTime = UtilTick.INSTANCE.secondsToTicks(3);
    @Config.Name("Slowness power")
    public int slownessPower = 2;
}
