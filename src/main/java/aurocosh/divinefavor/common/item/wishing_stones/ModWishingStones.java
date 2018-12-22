package aurocosh.divinefavor.common.item.wishing_stones;

import aurocosh.divinefavor.common.item.talismans.ItemTalisman;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.spirit.ModSpirit;

import java.util.Collection;

public final class ModWishingStones {
    public static void preInit() {
        Collection<ModSpirit> spirits = ModRegistries.spirits.getValues();
        for (ModSpirit spirit : spirits)
            for (ItemTalisman talisman : spirit.getTalismans())
                ModRegistries.items.register(new ItemWishingStone(spirit, talisman));
    }
}