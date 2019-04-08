package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.common.config.punishment.*;
import aurocosh.divinefavor.common.constants.ConstMisc;
import net.minecraftforge.common.config.Config;

@Config(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_ID + "/punishments")
public class ConfigPunishments {
    @Config.Name("Blizrabi")
    public static BlizrabiPunishmentConfig blizrabi = new BlizrabiPunishmentConfig();
    @Config.Name("Endererer")
    public static EnderererPunishmentConfig endererer = new EnderererPunishmentConfig();
    @Config.Name("Loon")
    public static LoonPunishmentConfig loon = new LoonPunishmentConfig();
    @Config.Name("Neblaze")
    public static NeblazePunishmentConfig neblaze = new NeblazePunishmentConfig();
    @Config.Name("Romol")
    public static RomolPunishmentConfig romol = new RomolPunishmentConfig();
    @Config.Name("Squarefury")
    public static SquarefuryPunishmentConfig squarefury = new SquarefuryPunishmentConfig();
}