package aurocosh.divinefavor.common.item.wishing_stones;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;

import java.util.ArrayList;
import java.util.List;

public final class ModWishingStones {
    public static final List<ItemWishingStone> wishingStones = new ArrayList<>();

    public static void preInit() {
        List<ModFavor> spirits = ModMappers.favors.getValues();
        for (ModFavor favor : spirits)
            wishingStones.add(new ItemWishingStone(favor, 10, "minor"));
    }
}