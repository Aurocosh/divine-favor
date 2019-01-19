package aurocosh.divinefavor.common.item.talismans.common;

import aurocosh.divinefavor.common.item.talismans.arrow.SpellHandSwap;
import aurocosh.divinefavor.common.item.talismans.base.arrow.ArrowSpellCurse;
import aurocosh.divinefavor.common.item.talismans.base.arrow.ArrowType;
import aurocosh.divinefavor.common.item.talismans.base.arrow.ItemArrowTalisman;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import aurocosh.divinefavor.common.util.UtilTick;

import java.awt.*;

public class ModArrowTalismans {
    public static ItemArrowTalisman roots;
    public static ItemArrowTalisman hand_swap;

    public static void preInit() {
        roots = ModRegistries.items.register(new ItemArrowTalisman("roots", 10, new ArrowSpellCurse(ModPotions.roots, UtilTick.secondsToTicks(15)), Color.green.getRGB(), ArrowType.CURSED_ARROW));
        hand_swap = ModRegistries.items.register(new ItemArrowTalisman("hand_swap", 10, new SpellHandSwap(), Color.green.getRGB(), ArrowType.SPELL_ARROW));

        ModMappers.talismans.register(ModRegistries.items.getValues(ItemArrowTalisman.class));
    }
}
