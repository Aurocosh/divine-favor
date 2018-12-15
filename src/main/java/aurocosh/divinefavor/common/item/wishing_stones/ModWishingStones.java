package aurocosh.divinefavor.common.item.wishing_stones;

import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.registry.ModRegistries;

public final class ModWishingStones {
    public static ItemWishingStone allfire_wishing_stone;
    public static ItemWishingStone blizrabi_wishing_stone;
    public static ItemWishingStone redwind_wishing_stone;
    public static ItemWishingStone romol_wishing_stone;
    public static ItemWishingStone squarefury_wishing_stone;
    public static ItemWishingStone timber_wishing_stone;

    public static void preInit() {
        allfire_wishing_stone = ModRegistries.items.register(new ItemWishingStone("allfire", ModFavors.favor_of_allfire, 1));
        blizrabi_wishing_stone = ModRegistries.items.register(new ItemWishingStone("blizrabi", ModFavors.favor_of_blizrabi, 1));
        redwind_wishing_stone = ModRegistries.items.register(new ItemWishingStone("redwind", ModFavors.favor_of_redwind, 1));
        romol_wishing_stone = ModRegistries.items.register(new ItemWishingStone("romol", ModFavors.favor_of_romol, 1));
        squarefury_wishing_stone = ModRegistries.items.register(new ItemWishingStone("squarefury", ModFavors.favor_of_squarefury, 1));
        timber_wishing_stone = ModRegistries.items.register(new ItemWishingStone("timber", ModFavors.favor_of_timber, 1));
    }
}