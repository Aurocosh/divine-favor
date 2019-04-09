package aurocosh.divinefavor.common.item.talismans.arrow.common;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.*;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowTalismanCurse;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import aurocosh.divinefavor.common.spirit.ModSpirits;

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
    public static ItemArrowTalisman reinforced_arrow_1;
    public static ItemArrowTalisman reinforced_arrow_2;
    public static ItemArrowTalisman reinforced_arrow_3;

    public static void preInit() {
        // arbow;
        anti_gravity_arrow = new ArrowTalismanAntiGravityArrow("anti_gravity_arrow", ModSpirits.arbow, ConfigArrow.antiGravityArrow.favorCost, Color.green.getRGB(), ConfigArrow.antiGravityArrow.damage, ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW);
        disarm = new ArrowTalismanDisarm("disarm", ModSpirits.arbow, ConfigArrow.disarm.favorCost, Color.orange.getRGB(), ConfigArrow.disarm.damage, ArrowOptions.REQUIRES_TARGET, ArrowType.SPELL_ARROW);
        hand_swap = new ArrowTalismanHandSwap("hand_swap", ModSpirits.arbow, ConfigArrow.handSwap.favorCost, Color.orange.getRGB(), ConfigArrow.handSwap.damage, ArrowOptions.REQUIRES_TARGET, ArrowType.SPELL_ARROW);
        zero_g_arrow = new ArrowTalismanZeroGArrow("zero_g_arrow", ModSpirits.arbow, 15, Color.green.getRGB(), 2, ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW);
        reinforced_arrow_1 = new ArrowTalismanHandSwap("reinforced_arrow_1", ModSpirits.arbow, ConfigArrow.reinforcedArrow1.favorCost, Color.red.getRGB(), ConfigArrow.reinforcedArrow1.damage, ArrowOptions.NORMAL, ArrowType.SPELL_ARROW);
        reinforced_arrow_2 = new ArrowTalismanHandSwap("reinforced_arrow_2", ModSpirits.arbow, ConfigArrow.reinforcedArrow2.favorCost, Color.red.getRGB(), ConfigArrow.reinforcedArrow2.damage, ArrowOptions.NORMAL, ArrowType.SPELL_ARROW);
        reinforced_arrow_3 = new ArrowTalismanHandSwap("reinforced_arrow_3", ModSpirits.arbow, ConfigArrow.reinforcedArrow3.favorCost, Color.red.getRGB(), ConfigArrow.reinforcedArrow3.damage, ArrowOptions.NORMAL, ArrowType.SPELL_ARROW);

        // blizrabi;

        // endererer;
        blink_arrow = new ArrowTalismanBlinkArrow("blink_arrow", ModSpirits.endererer, ConfigArrow.blinkArrow.favorCost, Color.green.getRGB(), ConfigArrow.blinkArrow.damage, ArrowOptions.ARROW_BREAKS, ArrowType.SPELL_ARROW);
        nether_swap = new ArrowTalismanNetherSwap("nether_swap", ModSpirits.endererer, ConfigArrow.netherSwap.favorCost, Color.green.getRGB(), ConfigArrow.netherSwap.damage, EnumSet.of(ArrowOptions.BreakOnHit, ArrowOptions.RequiresTarget), ArrowType.SPELL_ARROW);
        // loon;

        // neblaze;

        // redwind;

        // romol;

        // squarefury;

        // timber;
        armor_corrosion = new ArrowTalismanCurse("armor_corrosion", ModSpirits.timber, ConfigArrow.armorCorrosion.favorCost, Color.red.getRGB(), ModCurses.armor_corrosion, ConfigArrow.armorCorrosion.duration);
        crawling_mist = new ArrowTalismanCurse("crawling_mist", ModSpirits.timber, ConfigArrow.crawlingMist.favorCost, Color.red.getRGB(), ModCurses.crawling_mist, ConfigArrow.crawlingMist.duration);
        cripple = new ArrowTalismanCurse("cripple", ModSpirits.timber, ConfigArrow.cripple.favorCost, Color.red.getRGB(), ModCurses.cripple, ConfigArrow.cripple.duration);
        fiery_mark = new ArrowTalismanCurse("fiery_mark", ModSpirits.neblaze, ConfigArrow.fieryMark.favorCost, Color.red.getRGB(), ModCurses.fiery_mark, ConfigArrow.fieryMark.duration);
        fill_lungs = new ArrowTalismanCurse("fill_lungs", ModSpirits.blizrabi, ConfigArrow.fillLungs.favorCost, Color.red.getRGB(), ModCurses.fill_lungs, ConfigArrow.fillLungs.duration);
        hollow_leg = new ArrowTalismanCurse("hollow_leg", ModSpirits.timber, ConfigArrow.hollowLeg.favorCost, Color.red.getRGB(), ModCurses.hollow_leg, ConfigArrow.hollowLeg.duration);
        limp_leg = new ArrowTalismanCurse("limp_leg", ModSpirits.timber, ConfigArrow.limpLeg.favorCost, Color.red.getRGB(), ModCurses.limp_leg, ConfigArrow.limpLeg.duration);
        petrification = new ArrowTalismanCurse("petrification", ModSpirits.romol, ConfigArrow.petrification.favorCost, Color.red.getRGB(), ModCurses.petrification, ConfigArrow.petrification.duration);
        roots = new ArrowTalismanCurse("roots", ModSpirits.timber, ConfigArrow.roots.favorCost, Color.green.getRGB(), ModCurses.roots, ConfigArrow.roots.duration);
        skyfall = new ArrowTalismanCurse("skyfall", ModSpirits.redwind, ConfigArrow.skyfall.favorCost, Color.red.getRGB(), ModCurses.skyfall, ConfigArrow.skyfall.duration);
        suffocating_fumes = new ArrowTalismanCurse("suffocating_fumes", ModSpirits.timber, ConfigArrow.suffocatingFumes.favorCost, Color.red.getRGB(), ModCurses.suffocating_fumes, ConfigArrow.suffocatingFumes.duration);
        wind_leash = new ArrowTalismanCurse("wind_leash", ModSpirits.redwind, ConfigArrow.windLeash.favorCost, Color.red.getRGB(), ModCurses.wind_leash, ConfigArrow.windLeash.duration);
        yummy_smell = new ArrowTalismanCurse("yummy_smell", ModSpirits.timber, ConfigArrow.yummySmell.favorCost, Color.red.getRGB(), ModCurses.yummy_smell, ConfigArrow.yummySmell.duration);
    }
}
