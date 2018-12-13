package aurocosh.divinefavor.common.favors;

import aurocosh.divinefavor.common.constants.ConstFavorType;
import aurocosh.divinefavor.common.registry.ModRegistries;

public final class ModFavors {
    public static ModFavor favor_of_allfire;
    public static ModFavor favor_of_timber;
    public static ModFavor favor_of_romol;

    private static int nextId = 0;

    public static void preInit() {
        favor_of_allfire = ModRegistries.favors.register(new ModFavor(ConstFavorType.FAVOR_OF_ALLFIRE, nextId++));
        favor_of_timber = ModRegistries.favors.register(new ModFavor(ConstFavorType.FAVOR_OF_TIMBER, nextId++));
        favor_of_romol = ModRegistries.favors.register(new ModFavor(ConstFavorType.FAVOR_OF_ROMOL, nextId++));
    }
}