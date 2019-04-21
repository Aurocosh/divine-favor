package aurocosh.divinefavor.common.item.wishing_stones;

import aurocosh.divinefavor.common.config.common.ConfigItem;
import aurocosh.divinefavor.common.constants.ConstGemTabOrder;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;

import java.util.ArrayList;
import java.util.List;

public final class ModWishingStones {
    public static final List<ItemWishingStone> wishingStones = new ArrayList<>();

    public static void preInit() {
        List<ModSpirit> spirits = ModMappers.spirits.getValues();
        for (ModSpirit spirit : spirits)
            wishingStones.add(new ItemWishingStone(spirit, ConfigItem.minorWishingStoneFavorValue, "minor", ConstGemTabOrder.CALLING_STONE));
    }
}