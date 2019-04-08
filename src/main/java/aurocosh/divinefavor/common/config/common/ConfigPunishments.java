package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.common.config.punishment.BlizrabiPunishmentConfig;
import aurocosh.divinefavor.common.config.punishment.LoonPunishmentConfig;
import aurocosh.divinefavor.common.config.punishment.NeblazePunishmentConfig;
import aurocosh.divinefavor.common.constants.ConstMisc;
import net.minecraftforge.common.config.Config;

@Config(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_ID + "/punishments")
public class ConfigPunishments {
    @Config.Name("Blizrabi")
    public static BlizrabiPunishmentConfig blizrabi = new BlizrabiPunishmentConfig();
    @Config.Name("Loon")
    public static LoonPunishmentConfig loon = new LoonPunishmentConfig();
    @Config.Name("Neblaze")
    public static NeblazePunishmentConfig neblaze = new NeblazePunishmentConfig();
}