package aurocosh.divinefavor.common.item.wishing_stones;

import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.registry.ModRegistries;

public final class ModWishingStones {
    public static ItemWishingStone allfire_wishing_stone;
    public static ItemWishingStone timber_wishing_stone;
    public static ItemWishingStone romol_wishing_stone;

    public static void preInit() {
        allfire_wishing_stone = ModRegistries.items.register(new ItemWishingStone("allfire", ModFavors.favor_of_allfire, 1));
        timber_wishing_stone = ModRegistries.items.register(new ItemWishingStone("timber", ModFavors.favor_of_timber, 1));
        romol_wishing_stone = ModRegistries.items.register(new ItemWishingStone("romol", ModFavors.favor_of_romol, 1));
    }
}