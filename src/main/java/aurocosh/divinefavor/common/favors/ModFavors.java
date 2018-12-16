package aurocosh.divinefavor.common.favors;

import aurocosh.divinefavor.common.registry.ModRegistries;

public final class ModFavors {
    public static ModFavor favor_of_allfire;
    public static ModFavor favor_of_arbow;
    public static ModFavor favor_of_blizrabi;
    public static ModFavor favor_of_redwind;
    public static ModFavor favor_of_romol;
    public static ModFavor favor_of_squarefury;
    public static ModFavor favor_of_timber;

    private static int nextId = 0;

    public static void preInit() {
        favor_of_allfire = ModRegistries.favors.register(new ModFavor("allfire", nextId++));
        favor_of_arbow = ModRegistries.favors.register(new ModFavor("arbow", nextId++));
        favor_of_blizrabi = ModRegistries.favors.register(new ModFavor("blizrabi", nextId++));
        favor_of_redwind = ModRegistries.favors.register(new ModFavor("redwind", nextId++));
        favor_of_romol = ModRegistries.favors.register(new ModFavor("romol", nextId++));
        favor_of_squarefury = ModRegistries.favors.register(new ModFavor("squarefury", nextId++));
        favor_of_timber = ModRegistries.favors.register(new ModFavor("timber", nextId++));
    }
}