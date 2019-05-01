package aurocosh.divinefavor.common.config.entries.talismans.spell;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class EtherealFlash {
    @Config.Name("Favor cost")
    public int favorCost = 2;
    @Config.Name("Light level")
    public int lightLevel = 12;
    @Config.Name("Despawn delay")
    public int despawnDelay = UtilTick.INSTANCE.secondsToTicks(30);
}
