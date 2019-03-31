package aurocosh.divinefavor.common.config.auras;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class FrostyAura {
    @Config.Name("Time in snow biome")
    public int timeInSnowBiome = UtilTick.minutesToTicks(0.6f);
}
