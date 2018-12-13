package aurocosh.divinefavor.common.item.wishing_stones;

import aurocosh.divinefavor.common.constants.items.ConstWishingStoneNames;
import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.registry.ModRegistries;

public final class ModWishingStones {
    public static ModItem allfire_wishing_stone;
    public static ModItem timber_wishing_stone;
    public static ModItem romol_wishing_stone;

    public static void preInit() {
        allfire_wishing_stone = ModRegistries.items.register(new ItemWishingStone(ConstWishingStoneNames.ALLFIRE, ModFavors.favor_of_allfire, 1));
        timber_wishing_stone = ModRegistries.items.register(new ItemWishingStone(ConstWishingStoneNames.TIMBER, ModFavors.favor_of_timber, 1));
        romol_wishing_stone = ModRegistries.items.register(new ItemWishingStone(ConstWishingStoneNames.ROMOL, ModFavors.favor_of_romol, 1));
    }
}