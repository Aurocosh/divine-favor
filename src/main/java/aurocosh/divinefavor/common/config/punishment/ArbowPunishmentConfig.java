package aurocosh.divinefavor.common.config.punishment;

import aurocosh.divinefavor.common.config.data.TimeIntervalSeconds;
import net.minecraftforge.common.config.Config;

public class ArbowPunishmentConfig {
    @Config.Name("Damage")
    public int damage = 10;
    @Config.Name("Curses duration")
    public TimeIntervalSeconds curseDuration = new TimeIntervalSeconds(2 * 60, 5 * 60);
}
