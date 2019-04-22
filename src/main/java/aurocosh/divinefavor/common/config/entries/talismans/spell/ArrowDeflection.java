package aurocosh.divinefavor.common.config.entries.talismans.spell;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class ArrowDeflection {
    @Config.Name("Favor cost")
    public int favorCost = 150;
    @Config.Name("Duration")
    public int duration = UtilTick.minutesToTicks(1);
    @Config.Name("Duration decrease")
    public int durationDecrease = UtilTick.secondsToTicks(30);
    @Config.Name("Favor cost")
    public int deflectionCooldown = UtilTick.secondsToTicks(0.25f);
    @Config.Name("Radius")
    public float radius = 3;
    @Config.Name("Deflection tolerance")
    public float tolerance = 0.45f;
}
