package aurocosh.divinefavor.common.config.entries.talismans.spell;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class EscapePlan {
    @Config.Name("Favor cost")
    public int favorCost = 150;
    @Config.Name("Duration")
    public int duration = UtilTick.minutesToTicks(1);
}
