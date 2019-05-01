package aurocosh.divinefavor.common.config.entries.presences;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class FuriousPresence {
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.minutesToTicks(2);
    @Config.Name("Damage multiplier")
    public float damageMultiplier = 2.5f;
    @Config.Name("Min monsters to kill")
    public int minMonstersToKill = 5;
    @Config.Name("Max monsters to kill")
    public int maxMonstersToKill = 10;
}
