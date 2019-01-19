package aurocosh.divinefavor.common.item.talismans.common;

import aurocosh.divinefavor.common.item.talismans.arrow.SpellBlinkArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.SpellHandSwap;
import aurocosh.divinefavor.common.item.talismans.base.arrow.ArrowSpellCurse;
import aurocosh.divinefavor.common.item.talismans.base.arrow.ArrowSpellCurseTigger;
import aurocosh.divinefavor.common.item.talismans.base.arrow.ArrowType;
import aurocosh.divinefavor.common.item.talismans.base.arrow.ItemArrowTalisman;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import aurocosh.divinefavor.common.util.UtilTick;

import java.awt.*;

public class ModArrowTalismans {
    public static ItemArrowTalisman blink_arrow;
    public static ItemArrowTalisman fiery_mark;
    public static ItemArrowTalisman hand_swap;
    public static ItemArrowTalisman roots;

    public static void preInit() {
        blink_arrow = ModRegistries.items.register(new ItemArrowTalisman("blink_arrow", 15, new SpellBlinkArrow(), Color.green.getRGB(), true, ArrowType.SPELL_ARROW));
        fiery_mark = ModRegistries.items.register(new ItemArrowTalisman("fiery_mark", 10, new ArrowSpellCurseTigger(ModPotions.fiery_mark, UtilTick.secondsToTicks(10)), Color.red.getRGB(), false, ArrowType.CURSED_ARROW));
        hand_swap = ModRegistries.items.register(new ItemArrowTalisman("hand_swap", 10, new SpellHandSwap(), Color.yellow.getRGB(), false, ArrowType.SPELL_ARROW));
        roots = ModRegistries.items.register(new ItemArrowTalisman("roots", 10, new ArrowSpellCurse(ModPotions.roots, UtilTick.secondsToTicks(15)), Color.green.getRGB(), false, ArrowType.CURSED_ARROW));

        ModMappers.talismans.register(ModRegistries.items.getValues(ItemArrowTalisman.class));
    }
}
