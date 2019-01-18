package aurocosh.divinefavor.common.item.arrows.common;

import aurocosh.divinefavor.common.item.arrows.base.ItemCursedArrow;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.registry.ModRegistries;

import java.awt.*;

public class ModItemCursedArrows {
    public static ItemCursedArrow roots;

    public static void preInit() {
        roots = ModRegistries.arrows.register(new ItemCursedArrow("roots", ModPotions.roots, Color.blue.getRGB()));
    }
}
