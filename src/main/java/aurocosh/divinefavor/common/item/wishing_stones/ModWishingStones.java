package aurocosh.divinefavor.common.item.wishing_stones;

import aurocosh.divinefavor.common.constants.items.ConstWishingStoneNames;
import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.registry.CommonRegistry;
import aurocosh.divinefavor.common.talismans.ModTalismans;
import aurocosh.divinefavor.common.talismans.Talisman;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public final class ModWishingStones {
    private static List<WishingStone> wishingStonesList = new ArrayList<>();
    private static final Map<ResourceLocation, WishingStone> wishingStones = new HashMap<>();

    public static WishingStone allfire_wishing_stone;
    public static WishingStone timber_wishing_stone;
    public static WishingStone romol_wishing_stone;

    public static Collection<WishingStone> getValues() {
        return wishingStones.values();
    }

    public static ItemStack getStack(WishingStone wishingStone){
        return new ItemStack(ModItems.wishing_stone,1, getMeta(wishingStone));
    }

    public static int getMeta(WishingStone talisman){
        return wishingStonesList.indexOf(talisman);
    }

    public static WishingStone getByMeta(int meta){
        if(meta >= wishingStonesList.size())
            return null;
        return wishingStonesList.get(meta);
    }

    public static String[] getNames() {
        String[] names = new String[wishingStonesList.size()];
        for (int i = 0; i < wishingStonesList.size(); i++)
            names[i] = wishingStonesList.get(i).name;
        return names;
    }

    public static void preInit() {
        allfire_wishing_stone = register(new WishingStone(ConstWishingStoneNames.ALLFIRE, ModFavors.favor_of_allfire, 1));
        timber_wishing_stone = register(new WishingStone(ConstWishingStoneNames.TIMBER, ModFavors.favor_of_timber, 1));
        romol_wishing_stone = register(new WishingStone(ConstWishingStoneNames.ROMOL, ModFavors.favor_of_romol, 1));
    }

    public static void init() {
    }

    private static WishingStone register(WishingStone item) {
        wishingStones.put(item.getRegistryName(), item);
        wishingStonesList.add(item);
        CommonRegistry.register(item);
        return item;
    }
}