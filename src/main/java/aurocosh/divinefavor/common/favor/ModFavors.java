package aurocosh.divinefavor.common.favor;

public final class ModFavors {
    public static ModFavor allfire;
    public static ModFavor arbow;
    public static ModFavor blizrabi;
    public static ModFavor endererer;
    public static ModFavor nefir;
    public static ModFavor redwind;
    public static ModFavor romol;
    public static ModFavor squarefury;
    public static ModFavor timber;

    public static void preInit() {
        allfire = new ModFavor("allfire");
        arbow = new ModFavor("arbow");
        blizrabi = new ModFavor("blizrabi");
        endererer = new ModFavor("endererer");
        nefir = new ModFavor("nefir");
        redwind = new ModFavor("redwind");
        romol = new ModFavor("romol");
        squarefury = new ModFavor("squarefury");
        timber = new ModFavor("timber");
    }
}