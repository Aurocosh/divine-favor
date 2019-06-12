package aurocosh.divinefavor.common.config.entries.spell_talismans;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class ObsidianRoad {
    @Config.Name("Favor cost")
    public int favorCost = 20;
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.minutesToTicks(5);
}
