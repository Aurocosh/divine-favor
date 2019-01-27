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
        allfire = new ModFavor("allfire", 100, 100, 1000);
        arbow = new ModFavor("arbow", 100, 100, 1000);
        blizrabi = new ModFavor("blizrabi", 100, 100, 1000);
        endererer = new ModFavor("endererer", 100, 100, 1000);
        nefir = new ModFavor("nefir", 100, 100, 1000);
        redwind = new ModFavor("redwind", 100, 100, 1000);
        romol = new ModFavor("romol", 100, 100, 1000);
        squarefury = new ModFavor("squarefury", 100, 100, 1000);
        timber = new ModFavor("timber", 100, 100, 1000);
    }
}