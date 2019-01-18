package aurocosh.divinefavor.common.item.arrows.common;

import aurocosh.divinefavor.common.item.arrows.base.ItemSpellArrow;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.spell.arrow.SpellHandSwap;

import java.awt.*;

public class ModItemSpellArrows {
    public static ItemSpellArrow hand_swap;

    public static void preInit() {
        hand_swap = ModRegistries.arrows.register(new ItemSpellArrow("hand_swap", new SpellHandSwap(), Color.green.getRGB()));
    }
}
