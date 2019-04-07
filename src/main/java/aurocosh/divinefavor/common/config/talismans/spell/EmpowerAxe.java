package aurocosh.divinefavor.common.config.talismans.spell;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class EmpowerAxe {
    @Config.Name("Favor cost")
    public int favorCost = 20;
    @Config.Name("Duration")
    public int duration = UtilTick.minutesToTicks(1);
    @Config.Name("Instant break chance")
    public int instantBreakChance = 50;
    @Config.Name("Tool damage")
    public int toolDamage = 5;
}
