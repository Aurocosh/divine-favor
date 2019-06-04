package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.entries.SpiritConfig;
import aurocosh.divinefavor.common.config.entries.TimePeriodConfig;
import net.minecraftforge.common.config.Config;

@Config(modid = DivineFavor.MOD_ID, name = DivineFavor.MOD_ID + "/spirits")
public class ConfigSpirits {
    private final static int maxFavor = 50;

    @Config.Name("arbow")
    public static SpiritConfig arbow = new SpiritConfig("minecraft:leather", 3, new TimePeriodConfig(11, 15), 0, maxFavor, 0);
    @Config.Name("blizrabi")
    public static SpiritConfig blizrabi = new SpiritConfig("minecraft:fish", 6, new TimePeriodConfig(14, 18), 0, maxFavor, 0);
    @Config.Name("endererer")
    public static SpiritConfig endererer = new SpiritConfig("minecraft:ender_pearl", 2, new TimePeriodConfig(19, 23), 0, maxFavor, 0);
    @Config.Name("loon")
    public static SpiritConfig loon = new SpiritConfig("minecraft:quartz", 16, new TimePeriodConfig(1, 4), 0, maxFavor, 0);
    @Config.Name("neblaze")
    public static SpiritConfig neblaze = new SpiritConfig("minecraft:blaze_powder", 20, new TimePeriodConfig(10, 14), 0, maxFavor, 0);
    @Config.Name("redwind")
    public static SpiritConfig redwind = new SpiritConfig("minecraft:redstone", 20, new TimePeriodConfig(9, 12), 0, maxFavor, 0);
    @Config.Name("romol")
    public static SpiritConfig romol = new SpiritConfig("minecraft:gold_ingot", 2, new TimePeriodConfig(7, 10), 0, maxFavor, 0);
    @Config.Name("squarefury")
    public static SpiritConfig squarefury = new SpiritConfig("minecraft:bone", 16, new TimePeriodConfig(22, 2), 0, maxFavor, 0);
    @Config.Name("timber")
    public static SpiritConfig timber = new SpiritConfig("minecraft:nether_wart", 20, new TimePeriodConfig(18, 22), 0, maxFavor, 0);
}