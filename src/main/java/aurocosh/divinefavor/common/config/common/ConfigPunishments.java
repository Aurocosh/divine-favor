package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.common.config.punishment.LoonPunishmentConfig;
import aurocosh.divinefavor.common.constants.ConstMisc;
import net.minecraftforge.common.config.Config;

@Config(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_ID + "/punishments")
public class ConfigPunishments {
    @Config.Name("loon")
    public static LoonPunishmentConfig loon = new LoonPunishmentConfig();
}