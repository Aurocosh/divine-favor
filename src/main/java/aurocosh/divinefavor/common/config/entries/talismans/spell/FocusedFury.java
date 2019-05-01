package aurocosh.divinefavor.common.config.entries.talismans.spell;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class FocusedFury {
    @Config.Name("Favor cost")
    public int favorCost = 30;
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.minutesToTicks(5);
    @Config.Name("Extra damage")
    public float extraDamage = 10;
}
