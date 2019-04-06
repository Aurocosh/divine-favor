package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.common.config.FavorConfig;
import aurocosh.divinefavor.common.constants.ConstMisc;
import net.minecraftforge.common.config.Config;

@Config(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_ID + "/favors")
public class ConfigFavors {
    private final static int maxFavor = 50;

    @Config.Name("arbow")
    public static FavorConfig arbow = new FavorConfig(0, maxFavor, 0);
    @Config.Name("blizrabi")
    public static FavorConfig blizrabi = new FavorConfig(0, maxFavor, 0);
    @Config.Name("endererer")
    public static FavorConfig endererer = new FavorConfig(0, maxFavor, 0);
    @Config.Name("loon")
    public static FavorConfig loon = new FavorConfig(0, maxFavor, 0);
    @Config.Name("neblaze")
    public static FavorConfig neblaze = new FavorConfig(0, maxFavor, 0);
    @Config.Name("redwind")
    public static FavorConfig redwind = new FavorConfig(0, maxFavor, 0);
    @Config.Name("romol")
    public static FavorConfig romol = new FavorConfig(0, maxFavor, 0);
    @Config.Name("squarefury")
    public static FavorConfig squarefury = new FavorConfig(0, maxFavor, 0);
    @Config.Name("timber")
    public static FavorConfig timber = new FavorConfig(0, maxFavor, 0);
}