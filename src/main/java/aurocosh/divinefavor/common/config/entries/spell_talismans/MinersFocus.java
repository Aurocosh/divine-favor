package aurocosh.divinefavor.common.config.entries.spell_talismans;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class MinersFocus {
    @Config.Name("Favor cost")
    public int favorCost = 30;
    @Config.Name("Haste duration")
    public int hasteDuration = UtilTick.INSTANCE.minutesToTicks(2);
    @Config.Name("Haste level")
    public int hasteLevel = 4;
    @Config.Name("Fatigue duration")
    public int fatigueDuration = UtilTick.INSTANCE.minutesToTicks(2);
    @Config.Name("Fatigue level")
    public int fatigueLevel = 2;
}
