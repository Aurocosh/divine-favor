package aurocosh.divinefavor.common.config.entries.spell_talismans;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class WildSprint {
    @Config.Name("Favor cost")
    public int favorCost = 20;
    @Config.Name("Activation delay")
    public int activationDelay = UtilTick.INSTANCE.secondsToTicks(10);
    @Config.Name("Speed duration")
    public int speedDuration = UtilTick.INSTANCE.minutesToTicks(1f);
    @Config.Name("Speed level")
    public int speedLevel = 4;
    @Config.Name("Slowness force")
    public int slownessForce = 4;
}
