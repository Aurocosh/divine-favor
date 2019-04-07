package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.common.config.SpiritConfig;
import aurocosh.divinefavor.common.config.TimePeriodConfig;
import aurocosh.divinefavor.common.constants.ConstMisc;
import net.minecraftforge.common.config.Config;

@Config(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_ID + "/spirits")
public class ConfigSpirits {
    private final static int maxFavor = 50;

    @Config.Name("arbow")
    public static SpiritConfig arbow = new SpiritConfig("minecraft:leather", 3, new TimePeriodConfig(10, 14), 0, maxFavor, 0);
    @Config.Name("blizrabi")
    public static SpiritConfig blizrabi = new SpiritConfig("minecraft:fish", 6, new TimePeriodConfig(10, 14), 0, maxFavor, 0);
    @Config.Name("endererer")
    public static SpiritConfig endererer = new SpiritConfig("minecraft:ender_pearl", 2, new TimePeriodConfig(10, 14), 0, maxFavor, 0);
    @Config.Name("loon")
    public static SpiritConfig loon = new SpiritConfig("minecraft:quartz", 16, new TimePeriodConfig(10, 14), 0, maxFavor, 0);
    @Config.Name("neblaze")
    public static SpiritConfig neblaze = new SpiritConfig("minecraft:blaze_powder", 20, new TimePeriodConfig(10, 14), 0, maxFavor, 0);
    @Config.Name("redwind")
    public static SpiritConfig redwind = new SpiritConfig("minecraft:redstone", 20, new TimePeriodConfig(6, 18), 0, maxFavor, 0);
    @Config.Name("romol")
    public static SpiritConfig romol = new SpiritConfig("minecraft:gold_ingot", 2, new TimePeriodConfig(6, 18), 0, maxFavor, 0);
    @Config.Name("squarefury")
    public static SpiritConfig squarefury = new SpiritConfig("minecraft:bone", 16, new TimePeriodConfig(15, 21), 0, maxFavor, 0);
    @Config.Name("timber")
    public static SpiritConfig timber = new SpiritConfig("minecraft:nether_wart", 20, new TimePeriodConfig(6, 12), 0, maxFavor, 0);
}