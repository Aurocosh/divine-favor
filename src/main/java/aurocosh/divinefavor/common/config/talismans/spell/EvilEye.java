package aurocosh.divinefavor.common.config.talismans.spell;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class EvilEye {
    @Config.Name("Favor cost")
    public int favorCost = 20;
    @Config.Name("Starting severity")
    public int startingSeverity = 1;
    @Config.Name("Severity increase")
    public int severityIncrease = 1;
    @Config.Name("Max severity")
    public int maxSeverity = 3;
    @Config.Name("Damage per severity")
    public int damagePerSeverity = 10;
    @Config.Name("Slowness time")
    public int slownessTime = UtilTick.secondsToTicks(20);
    @Config.Name("Slowness level")
    public int slownessLevel = 2;
    @Config.Name("Evil eye time")
    public int evilEyeTime = UtilTick.secondsToTicks(10);
    @Config.Name("Opacity per severity")
    public float opacityPerSeverity = 0.33f;
}
