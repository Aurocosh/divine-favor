package aurocosh.divinefavor.common.config.entries.talismans.spell;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class WildSprint {
    @Config.Name("Favor cost")
    public int favorCost = 20;
    @Config.Name("Activation delay")
    public int activationDelay = UtilTick.secondsToTicks(10);
    @Config.Name("Speed duration")
    public int speedDuration = UtilTick.minutesToTicks(1f);
    @Config.Name("Speed level")
    public int speedLevel = 4;
    @Config.Name("Slowness force")
    public int slownessForce = 4;
}
