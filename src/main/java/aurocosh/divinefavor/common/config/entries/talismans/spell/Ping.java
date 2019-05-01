package aurocosh.divinefavor.common.config.entries.talismans.spell;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class Ping {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.secondsToTicks(5);
    @Config.Name("Particle intensity")
    public int particleIntensity = 3;
    @Config.Name("Render distance")
    public int renderDistance = 48;
}
