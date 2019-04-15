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

public class ModArrowTalismans {
    public static ItemArrowTalisman anti_gravity_arrow;
    public static ItemArrowTalisman armor_corrosion;
    public static ItemArrowTalisman blast_arrow;
    public static ItemArrowTalisman blink_arrow;
    public static ItemArrowTalisman climbing_arrow;
    public static ItemArrowTalisman crawling_mist;
    public static ItemArrowTalisman cripple;
    public static ItemArrowTalisman destructive_arrow_1;
    public static ItemArrowTalisman destructive_arrow_2;
    public static ItemArrowTalisman destructive_arrow_3;
    public static ItemArrowTalisman disarm;
    public static ItemArrowTalisman explosive_arrow;
    public static ItemArrowTalisman fiery_mark;
    public static ItemArrowTalisman fill_lungs;
    public static ItemArrowTalisman force_arrow;
    public static ItemArrowTalisman hand_swap;
    public static ItemArrowTalisman hollow_leg;
    public static ItemArrowTalisman hover_bubble_arrow;
    public static ItemArrowTalisman impulse_arrow;
    public static ItemArrowTalisman limp_leg;
    public static ItemArrowTalisman nether_swap;
    public static ItemArrowTalisman nuke_arrow;
    public static ItemArrowTalisman petrification;
    public static ItemArrowTalisman reinforced_arrow_1;
    public static ItemArrowTalisman reinforced_arrow_2;
    public static ItemArrowTalisman reinforced_arrow_3;
    public static ItemArrowTalisman roots;
    public static ItemArrowTalisman skyfall;
    public static ItemArrowTalisman suffocating_fumes;
    public static ItemArrowTalisman vacuum_arrow;
    public static ItemArrowTalisman wind_leash;
    public static ItemArrowTalisman yummy_smell;
    public static ItemArrowTalisman zero_g_arrow;

    public static void preInit() {
        // arbow;
        anti_gravity_arrow = new ArrowTalismanAntiGravityArrow("anti_gravity_arrow", ModSpirits.arbow, ConfigArrow.antiGravityArrow.favorCost, Color.green.getRGB(), ConfigArrow.antiGravityArrow.damage, ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW);
        blast_arrow = new ArrowTalismanExplosive("blast_arrow", ModSpirits.arbow, Color.yellow.getRGB(), ArrowOptions.ARROW_BREAKS, ArrowType.SPELL_ARROW, ConfigArrow.blastArrow);
        climbing_arrow = new ArrowTalismanClimbableArrow("climbing_arrow", ModSpirits.arbow, ConfigArrow.climbingArrow.favorCost, Color.green.getRGB(), ConfigArrow.climbingArrow.damage, ArrowOptions.NORMAL, ArrowType.SPELL_ARROW, ConfigArrow.climbingArrow.climbingSpeed, ConfigArrow.climbingArrow.climbingSpeed, ConfigArrow.climbingArrow.despawnDelay);
        hover_bubble_arrow = new ArrowTalismanHoverBubbleArrow("hover_bubble_arrow", ModSpirits.arbow, ConfigArrow.hoverBubbleArrow.favorCost, Color.green.getRGB(), ConfigArrow.hoverBubbleArrow.damage, ArrowOptions.NORMAL, ArrowType.SPELL_ARROW, ConfigArrow.hoverBubbleArrow.climbingSpeed, ConfigArrow.hoverBubbleArrow.climbingDistance, ConfigArrow.hoverBubbleArrow.despawnDelay);
        destructive_arrow_1 = new ArrowTalismanDestructiveArrow("destructive_arrow_1", ModSpirits.arbow, ConfigArrow.destructiveArrow1.favorCost, Color.green.getRGB(), ConfigArrow.destructiveArrow1.damage, ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW, ConfigArrow.destructiveArrow1.maxHardness);
        destructive_arrow_2 = new ArrowTalismanDestructiveArrow("destructive_arrow_2", ModSpirits.arbow, ConfigArrow.destructiveArrow2.favorCost, Color.green.getRGB(), ConfigArrow.destructiveArrow2.damage, ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW, ConfigArrow.destructiveArrow2.maxHardness);
        destructive_arrow_3 = new ArrowTalismanDestructiveArrow("destructive_arrow_3", ModSpirits.arbow, ConfigArrow.destructiveArrow3.favorCost, Color.green.getRGB(), ConfigArrow.destructiveArrow3.damage, ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW, ConfigArrow.destructiveArrow3.maxHardness);
        disarm = new ArrowTalismanDisarm("disarm", ModSpirits.arbow, ConfigArrow.disarm.favorCost, Color.orange.getRGB(), ConfigArrow.disarm.damage, ArrowOptions.REQUIRES_TARGET, ArrowType.SPELL_ARROW);
        explosive_arrow = new ArrowTalismanExplosive("explosive_arrow", ModSpirits.arbow, Color.blue.getRGB(), ArrowOptions.ARROW_BREAKS, ArrowType.SPELL_ARROW, ConfigArrow.explosiveArrow);
        force_arrow = new ArrowTalismanForceArrow("force_arrow", ModSpirits.arbow, ConfigArrow.forceArrow.favorCost, Color.red.getRGB(), ConfigArrow.forceArrow.damage, ArrowType.SPELL_ARROW, ConfigArrow.forceArrow.velocity);
        hand_swap = new ArrowTalismanHandSwap("hand_swap", ModSpirits.arbow, ConfigArrow.handSwap.favorCost, Color.orange.getRGB(), ConfigArrow.handSwap.damage, ArrowOptions.REQUIRES_TARGET, ArrowType.SPELL_ARROW);
        impulse_arrow = new ArrowTalismanForceArrow("impulse_arrow", ModSpirits.arbow, ConfigArrow.impulseArrow.favorCost, Color.red.getRGB(), ConfigArrow.impulseArrow.damage, ArrowType.SPELL_ARROW, ConfigArrow.impulseArrow.velocity);
        nuke_arrow = new ArrowTalismanExplosive("nuke_arrow", ModSpirits.arbow, Color.red.getRGB(), ArrowOptions.ARROW_BREAKS, ArrowType.SPELL_ARROW, ConfigArrow.nukeArrow);
        reinforced_arrow_1 = new ItemArrowTalisman("reinforced_arrow_1", ModSpirits.arbow, ConfigArrow.reinforcedArrow1.favorCost, Color.yellow.getRGB(), ConfigArrow.reinforcedArrow1.damage, ArrowOptions.NORMAL, ArrowType.SPELL_ARROW);
        reinforced_arrow_2 = new ItemArrowTalisman("reinforced_arrow_2", ModSpirits.arbow, ConfigArrow.reinforcedArrow2.favorCost, Color.blue.getRGB(), ConfigArrow.reinforcedArrow2.damage, ArrowOptions.NORMAL, ArrowType.SPELL_ARROW);
        reinforced_arrow_3 = new ItemArrowTalisman("reinforced_arrow_3", ModSpirits.arbow, ConfigArrow.reinforcedArrow3.favorCost, Color.red.getRGB(), ConfigArrow.reinforcedArrow3.damage, ArrowOptions.NORMAL, ArrowType.SPELL_ARROW);
        vacuum_arrow = new ArrowTalismanVacuumArrow("vacuum_arrow", ModSpirits.arbow, ConfigArrow.vacuumArrow.favorCost, Color.green.getRGB(), ConfigArrow.vacuumArrow.damage, ArrowOptions.NORMAL, ArrowType.SPELL_ARROW);
        zero_g_arrow = new ArrowTalismanZeroGArrow("zero_g_arrow", ModSpirits.arbow, ConfigArrow.zeroGArrow.favorCost, Color.green.getRGB(), ConfigArrow.zeroGArrow.damage, ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW);

        // blizrabi;

        // endererer;
        blink_arrow = new ArrowTalismanBlinkArrow("blink_arrow", ModSpirits.endererer, ConfigArrow.blinkArrow.favorCost, Color.green.getRGB(), ConfigArrow.blinkArrow.damage, ArrowOptions.ARROW_BREAKS, ArrowType.SPELL_ARROW);
        nether_swap = new ArrowTalismanNetherSwap("nether_swap", ModSpirits.endererer, ConfigArrow.netherSwap.favorCost, Color.green.getRGB(), ConfigArrow.netherSwap.damage, ArrowType.SPELL_ARROW);
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
