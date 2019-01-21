package aurocosh.divinefavor.common.item.talismans.arrow.common;

import aurocosh.divinefavor.common.item.talismans.arrow.*;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowTalismanCurse;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import aurocosh.divinefavor.common.util.UtilTick;

import java.awt.*;

public class ModArrowTalismans {
    public static ItemArrowTalisman anti_gravity_arrow;
    public static ItemArrowTalisman armor_corrosion;
    public static ItemArrowTalisman blink_arrow;
    public static ItemArrowTalisman disarm;
    public static ItemArrowTalisman fiery_mark;
    public static ItemArrowTalisman hand_swap;
    public static ItemArrowTalisman limp_leg;
    public static ItemArrowTalisman nether_swap;
    public static ItemArrowTalisman petrification;
    public static ItemArrowTalisman roots;
    public static ItemArrowTalisman skyfall;
    public static ItemArrowTalisman wind_leash;
    public static ItemArrowTalisman zero_g_arrow;

    public static void preInit() {
        anti_gravity_arrow = ModRegistries.items.register(new ArrowTalismanAntiGravityArrow());
        armor_corrosion = ModRegistries.items.register(new ArrowTalismanCurse("armor_corrosion", 10, Color.red.getRGB(), ModCurses.armor_corrosion, UtilTick.minutesToTicks(3)));
        blink_arrow = ModRegistries.items.register(new ArrowTalismanBlinkArrow());
        disarm = ModRegistries.items.register(new ArrowTalismanDisarm());
        fiery_mark = ModRegistries.items.register(new ArrowTalismanCurse("fiery_mark", 10, Color.red.getRGB(), ModCurses.fiery_mark, UtilTick.secondsToTicks(10)));
        hand_swap = ModRegistries.items.register(new ArrowTalismanHandSwap());
        limp_leg = ModRegistries.items.register(new ArrowTalismanCurse("limp_leg", 10, Color.red.getRGB(), ModCurses.limp_leg, UtilTick.secondsToTicks(60)));
        nether_swap = ModRegistries.items.register(new ArrowTalismanNetherSwap());
        petrification = ModRegistries.items.register(new ArrowTalismanCurse("petrification", 10, Color.red.getRGB(), ModCurses.petrification, UtilTick.secondsToTicks(40)));
        roots = ModRegistries.items.register(new ArrowTalismanCurse("roots", 10, Color.green.getRGB(), ModCurses.roots, UtilTick.secondsToTicks(15)));
        skyfall = ModRegistries.items.register(new ArrowTalismanCurse("skyfall", 10, Color.red.getRGB(), ModCurses.skyfall, UtilTick.secondsToTicks(8)));
        wind_leash = ModRegistries.items.register(new ArrowTalismanCurse("wind_leash", 10, Color.red.getRGB(), ModCurses.wind_leash, UtilTick.secondsToTicks(12)));
        zero_g_arrow = ModRegistries.items.register(new ArrowTalismanZeroGArrow());

        ModMappers.talismans.register(ModRegistries.items.getValues(ItemArrowTalisman.class));
    }
}
