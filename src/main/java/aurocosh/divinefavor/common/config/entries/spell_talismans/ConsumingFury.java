package aurocosh.divinefavor.common.config.entries.spell_talismans;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class ConsumingFury {
    @Config.Name("Favor cost")
    public int favorCost = 10;
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.minutesToTicks(5);
    @Config.Name("Mobs to kill")
    public int mobsToKill = 5;
    @Config.Name("Extra damage")
    public float extraDamage = 16;
    @Config.Name("Punushment damage")
    public float punishmentDamage = 20;
}
