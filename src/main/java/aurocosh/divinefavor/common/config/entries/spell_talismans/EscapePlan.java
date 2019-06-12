package aurocosh.divinefavor.common.config.entries.spell_talismans;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class EscapePlan {
    @Config.Name("Favor cost")
    public int favorCost = 150;
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.minutesToTicks(1);
}
