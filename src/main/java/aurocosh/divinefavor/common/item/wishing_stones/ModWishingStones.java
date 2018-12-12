package aurocosh.divinefavor.common.item.wishing_stones;

import aurocosh.divinefavor.common.constants.items.ConstWishingStoneNames;
import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.registry.MetaItemContainer;
import aurocosh.divinefavor.common.registry.interfaces.IMetaContainer;

public final class ModWishingStones {
    private static final MetaItemContainer<WishingStone> wishingStones = new MetaItemContainer<>();

    public static WishingStone allfire_wishing_stone;
    public static WishingStone timber_wishing_stone;
    public static WishingStone romol_wishing_stone;

    public static IMetaContainer<WishingStone> getMetaContainer() {
        return wishingStones;
    }

    public static void preInit() {
        allfire_wishing_stone = wishingStones.register(new WishingStone(ConstWishingStoneNames.ALLFIRE, ModFavors.favor_of_allfire, 1));
        timber_wishing_stone = wishingStones.register(new WishingStone(ConstWishingStoneNames.TIMBER, ModFavors.favor_of_timber, 1));
        romol_wishing_stone = wishingStones.register(new WishingStone(ConstWishingStoneNames.ROMOL, ModFavors.favor_of_romol, 1));
    }
}