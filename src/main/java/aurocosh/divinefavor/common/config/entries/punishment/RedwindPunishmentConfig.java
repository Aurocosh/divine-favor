package aurocosh.divinefavor.common.config.entries.punishment;

import aurocosh.divinefavor.common.config.data.IntInterval;
import aurocosh.divinefavor.common.config.data.TimeIntervalSeconds;
import net.minecraftforge.common.config.Config;

public class RedwindPunishmentConfig {
    @Config.Name("Drag speed")
    public int dragSpeed = 4;
    @Config.Name("Drag count")
    public IntInterval dragCount = new IntInterval(3, 7);
    @Config.Name("Drag duration")
    public TimeIntervalSeconds dragDuration = new TimeIntervalSeconds(5, 30);
}
