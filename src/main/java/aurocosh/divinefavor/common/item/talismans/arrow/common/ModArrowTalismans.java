package aurocosh.divinefavor.common.item.talismans.arrow.common;

import aurocosh.divinefavor.common.favor.ModFavors;
import aurocosh.divinefavor.common.item.talismans.arrow.*;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowTalismanCurse;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import aurocosh.divinefavor.common.util.UtilTick;

import java.awt.*;
import java.util.EnumSet;

public class ModArrowTalismans {
    public static ItemArrowTalisman anti_gravity_arrow;
    public static ItemArrowTalisman armor_corrosion;
    public static ItemArrowTalisman blink_arrow;
    public static ItemArrowTalisman crawling_mist;
    public static ItemArrowTalisman cripple;
    public static ItemArrowTalisman disarm;
    public static ItemArrowTalisman fiery_mark;
    public static ItemArrowTalisman fill_lungs;
    public static ItemArrowTalisman hand_swap;
    public static ItemArrowTalisman hollow_leg;
    public static ItemArrowTalisman limp_leg;
    public static ItemArrowTalisman nether_swap;
    public static ItemArrowTalisman petrification;
    public static ItemArrowTalisman roots;
    public static ItemArrowTalisman skyfall;
    public static ItemArrowTalisman suffocating_fumes;
    public static ItemArrowTalisman wind_leash;
    public static ItemArrowTalisman yummy_smell;
    public static ItemArrowTalisman zero_g_arrow;

    public static void preInit() {

        // arbow;
        anti_gravity_arrow = new ArrowTalismanAntiGravityArrow("anti_gravity_arrow", ModFavors.redwind, 1, Color.green.getRGB(), 2, ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW);
        disarm = new ArrowTalismanDisarm("disarm", ModFavors.timber, 1, Color.orange.getRGB(), 0, ArrowOptions.REQUIRES_TARGET, ArrowType.SPELL_ARROW);
        hand_swap = new ArrowTalismanHandSwap("hand_swap", ModFavors.timber, 1, Color.orange.getRGB(), 0, ArrowOptions.REQUIRES_TARGET, ArrowType.SPELL_ARROW);
        zero_g_arrow = new ArrowTalismanZeroGArrow("zero_g_arrow", ModFavors.redwind,15, Color.green.getRGB(), 2, ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW);

        // blizrabi;

        // endererer;
        blink_arrow = new ArrowTalismanBlinkArrow("blink_arrow", ModFavors.endererer, 2, Color.green.getRGB(), 0, ArrowOptions.ARROW_BREAKS, ArrowType.SPELL_ARROW);
        nether_swap = new ArrowTalismanNetherSwap("nether_swap", ModFavors.endererer, 15, Color.green.getRGB(), 0, EnumSet.of(ArrowOptions.BreakOnHit, ArrowOptions.RequiresTarget), ArrowType.SPELL_ARROW);
        // loon;

        // neblaze;

        // redwind;

        // romol;

        // squarefury;

        // timber;
        armor_corrosion = new ArrowTalismanCurse("armor_corrosion", ModFavors.timber, 1, Color.red.getRGB(), ModCurses.armor_corrosion, UtilTick.minutesToTicks(3));
        crawling_mist = new ArrowTalismanCurse("crawling_mist", ModFavors.timber, 1, Color.red.getRGB(), ModCurses.crawling_mist, UtilTick.minutesToTicks(3));
        cripple = new ArrowTalismanCurse("cripple", ModFavors.timber, 1, Color.red.getRGB(), ModCurses.cripple, UtilTick.minutesToTicks(2));
        fiery_mark = new ArrowTalismanCurse("fiery_mark", ModFavors.neblaze, 1, Color.red.getRGB(), ModCurses.fiery_mark, UtilTick.secondsToTicks(10));
        fill_lungs = new ArrowTalismanCurse("fill_lungs", ModFavors.blizrabi, 1, Color.red.getRGB(), ModCurses.fill_lungs, UtilTick.minutesToTicks(2));
        hollow_leg = new ArrowTalismanCurse("hollow_leg", ModFavors.timber, 1, Color.red.getRGB(), ModCurses.hollow_leg, UtilTick.minutesToTicks(2));
        limp_leg = new ArrowTalismanCurse("limp_leg", ModFavors.timber, 1, Color.red.getRGB(), ModCurses.limp_leg, UtilTick.secondsToTicks(60));
        petrification = new ArrowTalismanCurse("petrification", ModFavors.romol, 1, Color.red.getRGB(), ModCurses.petrification, UtilTick.secondsToTicks(40));
        roots = new ArrowTalismanCurse("roots", ModFavors.timber, 1, Color.green.getRGB(), ModCurses.roots, UtilTick.secondsToTicks(15));
        skyfall = new ArrowTalismanCurse("skyfall", ModFavors.redwind, 1, Color.red.getRGB(), ModCurses.skyfall, UtilTick.secondsToTicks(8));
        suffocating_fumes = new ArrowTalismanCurse("suffocating_fumes", ModFavors.timber, 1, Color.red.getRGB(), ModCurses.suffocating_fumes, UtilTick.minutesToTicks(2));
        wind_leash = new ArrowTalismanCurse("wind_leash", ModFavors.redwind,1, Color.red.getRGB(), ModCurses.wind_leash, UtilTick.secondsToTicks(12));
        yummy_smell = new ArrowTalismanCurse("yummy_smell", ModFavors.timber,1, Color.red.getRGB(), ModCurses.yummy_smell, UtilTick.secondsToTicks(30));
    }
}
