package aurocosh.divinefavor.common.favor;

import aurocosh.divinefavor.common.config.common.ConfigFavors;

public final class ModFavors {
    public static ModFavor arbow;
    public static ModFavor blizrabi;
    public static ModFavor endererer;
    public static ModFavor loon;
    public static ModFavor neblaze;
    public static ModFavor redwind;
    public static ModFavor romol;
    public static ModFavor squarefury;
    public static ModFavor timber;

    public static void preInit() {
        arbow = new ModFavor("arbow", ConfigFavors.arbow);
        blizrabi = new ModFavor("blizrabi", ConfigFavors.blizrabi);
        endererer = new ModFavor("endererer", ConfigFavors.endererer);
        loon = new ModFavor("loon", ConfigFavors.loon);
        neblaze = new ModFavor("neblaze", ConfigFavors.neblaze);
        redwind = new ModFavor("redwind", ConfigFavors.redwind);
        romol = new ModFavor("romol", ConfigFavors.romol);
        squarefury = new ModFavor("squarefury", ConfigFavors.squarefury);
        timber = new ModFavor("timber", ConfigFavors.timber);
    }
}