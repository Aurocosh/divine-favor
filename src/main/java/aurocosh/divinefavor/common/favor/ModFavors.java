package aurocosh.divinefavor.common.favor;

public final class ModFavors {
    public static ModFavor neblaze;
    public static ModFavor arbow;
    public static ModFavor blizrabi;
    public static ModFavor endererer;
    public static ModFavor redwind;
    public static ModFavor romol;
    public static ModFavor squarefury;
    public static ModFavor timber;

    public static void preInit() {
        arbow = new ModFavor("arbow");
        blizrabi = new ModFavor("blizrabi");
        endererer = new ModFavor("endererer");
        neblaze = new ModFavor("neblaze");
        redwind = new ModFavor("redwind");
        romol = new ModFavor("romol");
        squarefury = new ModFavor("squarefury");
        timber = new ModFavor("timber");
    }
}