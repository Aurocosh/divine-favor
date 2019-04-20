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
    public static ItemArrowTalisman flak_arrow;
    public static ItemArrowTalisman force_arrow;
    public static ItemArrowTalisman hand_swap;
    public static ItemArrowTalisman high_speed_arrow;
    public static ItemArrowTalisman hollow_leg;
    public static ItemArrowTalisman hover_bubble_arrow;
    public static ItemArrowTalisman hyper_speed_arrow;
    public static ItemArrowTalisman impulse_arrow;
    public static ItemArrowTalisman incendiary_arrow;
    public static ItemArrowTalisman life_steal_arrow;
    public static ItemArrowTalisman limp_leg;
    public static ItemArrowTalisman mine_arrow;
    public static ItemArrowTalisman nether_swap;
    public static ItemArrowTalisman nuke_arrow;
    public static ItemArrowTalisman petrification;
    public static ItemArrowTalisman piercing_arrow;
    public static ItemArrowTalisman reinforced_arrow_1;
    public static ItemArrowTalisman reinforced_arrow_2;
    public static ItemArrowTalisman reinforced_arrow_3;
    public static ItemArrowTalisman ricochet_arrow;
    public static ItemArrowTalisman roots;
    public static ItemArrowTalisman skyfall;
    public static ItemArrowTalisman sniper_arrow;
    public static ItemArrowTalisman spooky_arrow;
    public static ItemArrowTalisman stasis_arrow;
    public static ItemArrowTalisman suffocating_fumes;
    public static ItemArrowTalisman tracer_arrow;
    public static ItemArrowTalisman vacuum_arrow;
    public static ItemArrowTalisman wind_leash;
    public static ItemArrowTalisman yummy_smell;
    public static ItemArrowTalisman zero_g_arrow;

    public static void preInit() {
        Color enderererColor = new Color(0, 124, 86);
        Color genericTier1 = new Color(255, 232, 109);
        Color genericTier2 = new Color(51, 163, 255);
        Color genericTier3 = new Color(255, 110, 31);
        Color climbingColor = new Color(124, 78, 47);

        // arbow;
        anti_gravity_arrow = new ArrowTalismanAntiGravityArrow("anti_gravity_arrow", ModSpirits.arbow, ConfigArrow.antiGravityArrow.favorCost, Color.green, ConfigArrow.antiGravityArrow.damage, ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW);
        blast_arrow = new ArrowTalismanExplosive("blast_arrow", ModSpirits.arbow, genericTier1, ArrowOptions.ARROW_BREAKS, ArrowType.SPELL_ARROW, ConfigArrow.blastArrow);
        climbing_arrow = new ArrowTalismanClimbableArrow("climbing_arrow", ModSpirits.arbow, ConfigArrow.climbingArrow.favorCost, climbingColor, ConfigArrow.climbingArrow.damage, ArrowOptions.NORMAL, ArrowType.SPELL_ARROW, ConfigArrow.climbingArrow.climbingSpeed, ConfigArrow.climbingArrow.climbingDistance, ConfigArrow.climbingArrow.despawnDelay);
        destructive_arrow_1 = new ArrowTalismanDestructiveArrow("destructive_arrow_1", ModSpirits.arbow, ConfigArrow.destructiveArrow1.favorCost, genericTier1, ConfigArrow.destructiveArrow1.damage, ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW, ConfigArrow.destructiveArrow1.maxHardness);
        destructive_arrow_2 = new ArrowTalismanDestructiveArrow("destructive_arrow_2", ModSpirits.arbow, ConfigArrow.destructiveArrow2.favorCost, genericTier2, ConfigArrow.destructiveArrow2.damage, ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW, ConfigArrow.destructiveArrow2.maxHardness);
        destructive_arrow_3 = new ArrowTalismanDestructiveArrow("destructive_arrow_3", ModSpirits.arbow, ConfigArrow.destructiveArrow3.favorCost, genericTier3, ConfigArrow.destructiveArrow3.damage, ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW, ConfigArrow.destructiveArrow3.maxHardness);
        disarm = new ArrowTalismanDisarm("disarm", ModSpirits.arbow, ConfigArrow.disarm.favorCost, Color.orange, ConfigArrow.disarm.damage, ArrowOptions.REQUIRES_TARGET, ArrowType.SPELL_ARROW);
        explosive_arrow = new ArrowTalismanExplosive("explosive_arrow", ModSpirits.arbow, genericTier2, ArrowOptions.ARROW_BREAKS, ArrowType.SPELL_ARROW, ConfigArrow.explosiveArrow);
        flak_arrow = new ArrowTalismanFlakArrow("flak_arrow", ModSpirits.arbow, ConfigArrow.flakArrow.favorCost, new Color(185, 100, 110), ConfigArrow.flakArrow.damage, ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW);
        force_arrow = new ArrowTalismanForceArrow("force_arrow", ModSpirits.arbow, ConfigArrow.forceArrow.favorCost, new Color(65, 64, 69), ConfigArrow.forceArrow.damage, ArrowType.SPELL_ARROW, ConfigArrow.forceArrow.velocity);
        hand_swap = new ArrowTalismanHandSwap("hand_swap", ModSpirits.arbow, ConfigArrow.handSwap.favorCost, Color.orange, ConfigArrow.handSwap.damage, ArrowOptions.REQUIRES_TARGET, ArrowType.SPELL_ARROW);
        high_speed_arrow = new ArrowTalismanHighSpeedArrow("high_speed_arrow", ModSpirits.arbow, ConfigArrow.highSpeedArrow.favorCost, genericTier2, ConfigArrow.highSpeedArrow.damage, ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW, ConfigArrow.highSpeedArrow.extraVelocity);
        hover_bubble_arrow = new ArrowTalismanHoverBubbleArrow("hover_bubble_arrow", ModSpirits.arbow, ConfigArrow.hoverBubbleArrow.favorCost, climbingColor, ConfigArrow.hoverBubbleArrow.damage, ArrowOptions.NORMAL, ArrowType.SPELL_ARROW, ConfigArrow.hoverBubbleArrow.climbingSpeed, ConfigArrow.hoverBubbleArrow.climbingDistance, ConfigArrow.hoverBubbleArrow.despawnDelay);
        hyper_speed_arrow = new ArrowTalismanHighSpeedArrow("hyper_speed_arrow", ModSpirits.arbow, ConfigArrow.hyperSpeedArrow.favorCost, genericTier3, ConfigArrow.hyperSpeedArrow.damage, ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW, ConfigArrow.hyperSpeedArrow.extraVelocity);
        impulse_arrow = new ArrowTalismanForceArrow("impulse_arrow", ModSpirits.arbow, ConfigArrow.impulseArrow.favorCost,  new Color(114, 113, 118), ConfigArrow.impulseArrow.damage, ArrowType.SPELL_ARROW, ConfigArrow.impulseArrow.velocity);
        incendiary_arrow = new ArrowTalismanIncendiaryArrow("incendiary_arrow", ModSpirits.arbow, ConfigArrow.incendiaryArrow.favorCost,  new Color(180, 80, 0), ConfigArrow.incendiaryArrow.damage, ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW);
        life_steal_arrow = new ArrowTalismanLifeStealArrow("life_steal_arrow", ModSpirits.arbow, ConfigArrow.lifeStealArrow.favorCost,  new Color(16, 211, 0), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW);
        mine_arrow = new ArrowTalismanMineArrow("mine_arrow", ModSpirits.arbow, ConfigArrow.mineArrow.favorCost,  new Color(164, 163, 168), ConfigArrow.mineArrow.damage, ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW);
        nuke_arrow = new ArrowTalismanExplosive("nuke_arrow", ModSpirits.arbow, genericTier3, ArrowOptions.ARROW_BREAKS, ArrowType.SPELL_ARROW, ConfigArrow.nukeArrow);
        piercing_arrow = new ArrowTalismanPiercingArrow("piercing_arrow", ModSpirits.arbow, ConfigArrow.piercingArrow.favorCost,  new Color(100, 104, 168), ConfigArrow.piercingArrow.damage, ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW);
        reinforced_arrow_1 = new ItemArrowTalisman("reinforced_arrow_1", ModSpirits.arbow, ConfigArrow.reinforcedArrow1.favorCost, genericTier1, ConfigArrow.reinforcedArrow1.damage, ArrowOptions.NORMAL, ArrowType.SPELL_ARROW);
        reinforced_arrow_2 = new ItemArrowTalisman("reinforced_arrow_2", ModSpirits.arbow, ConfigArrow.reinforcedArrow2.favorCost, genericTier2, ConfigArrow.reinforcedArrow2.damage, ArrowOptions.NORMAL, ArrowType.SPELL_ARROW);
        reinforced_arrow_3 = new ItemArrowTalisman("reinforced_arrow_3", ModSpirits.arbow, ConfigArrow.reinforcedArrow3.favorCost, genericTier3, ConfigArrow.reinforcedArrow3.damage, ArrowOptions.NORMAL, ArrowType.SPELL_ARROW);
        ricochet_arrow = new ArrowTalismanRicochetArrow("ricochet_arrow", ModSpirits.arbow, ConfigArrow.ricochetArrow.favorCost,  new Color(168, 24, 105), ConfigArrow.ricochetArrow.damage, ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW);
        sniper_arrow = new ArrowTalismanSniperArrow("sniper_arrow", ModSpirits.arbow, ConfigArrow.sniperArrow.favorCost,  new Color(206, 206, 0), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW);
        spooky_arrow = new ArrowTalismanSpookyArrow("spooky_arrow", ModSpirits.arbow, ConfigArrow.spookyArrow.favorCost, Color.green, ConfigArrow.spookyArrow.damage, ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW);
        stasis_arrow = new ArrowTalismanStasisArrow("stasis_arrow", ModSpirits.arbow, ConfigArrow.stasisArrow.favorCost, new Color(164, 163, 168), ConfigArrow.stasisArrow.damage, ArrowOptions.NORMAL, ArrowType.SPELL_ARROW);
        tracer_arrow = new ArrowTalismanTracerArrow("tracer_arrow", ModSpirits.arbow, ConfigArrow.tracerArrow.favorCost,  new Color(203, 3, 0), ConfigArrow.tracerArrow.damage, ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW);
        vacuum_arrow = new ArrowTalismanVacuumArrow("vacuum_arrow", ModSpirits.arbow, ConfigArrow.vacuumArrow.favorCost,  new Color(83, 4, 124), ConfigArrow.vacuumArrow.damage, ArrowOptions.NORMAL, ArrowType.SPELL_ARROW);
        zero_g_arrow = new ArrowTalismanZeroGArrow("zero_g_arrow", ModSpirits.arbow, ConfigArrow.zeroGArrow.favorCost,  new Color(0, 168, 122), ConfigArrow.zeroGArrow.damage, ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW);

        // blizrabi;

        // endererer;
        blink_arrow = new ArrowTalismanBlinkArrow("blink_arrow", ModSpirits.endererer, ConfigArrow.blinkArrow.favorCost, enderererColor, ConfigArrow.blinkArrow.damage, ArrowOptions.ARROW_BREAKS, ArrowType.SPELL_ARROW);
        nether_swap = new ArrowTalismanNetherSwap("nether_swap", ModSpirits.endererer, ConfigArrow.netherSwap.favorCost, enderererColor, ConfigArrow.netherSwap.damage, ArrowType.SPELL_ARROW);
        // loon;

        // neblaze;

        // redwind;

        // romol;

        // squarefury;

        // timber;
        Color curseColor1 = new Color(88, 4, 130);
        Color curseColor2 = new Color(90, 23, 0);
        Color curseColor3 = new Color(90, 90, 0);
        armor_corrosion = new ArrowTalismanCurse("armor_corrosion", ModSpirits.timber, ConfigArrow.armorCorrosion.favorCost, curseColor1, ModCurses.armor_corrosion, ConfigArrow.armorCorrosion.duration);
        crawling_mist = new ArrowTalismanCurse("crawling_mist", ModSpirits.timber, ConfigArrow.crawlingMist.favorCost, curseColor2, ModCurses.crawling_mist, ConfigArrow.crawlingMist.duration);
        cripple = new ArrowTalismanCurse("cripple", ModSpirits.timber, ConfigArrow.cripple.favorCost, curseColor3, ModCurses.cripple, ConfigArrow.cripple.duration);
        fiery_mark = new ArrowTalismanCurse("fiery_mark", ModSpirits.neblaze, ConfigArrow.fieryMark.favorCost, curseColor1, ModCurses.fiery_mark, ConfigArrow.fieryMark.duration);
        fill_lungs = new ArrowTalismanCurse("fill_lungs", ModSpirits.blizrabi, ConfigArrow.fillLungs.favorCost, curseColor2, ModCurses.fill_lungs, ConfigArrow.fillLungs.duration);
        hollow_leg = new ArrowTalismanCurse("hollow_leg", ModSpirits.timber, ConfigArrow.hollowLeg.favorCost, curseColor3, ModCurses.hollow_leg, ConfigArrow.hollowLeg.duration);
        limp_leg = new ArrowTalismanCurse("limp_leg", ModSpirits.timber, ConfigArrow.limpLeg.favorCost, curseColor3, ModCurses.limp_leg, ConfigArrow.limpLeg.duration);
        petrification = new ArrowTalismanCurse("petrification", ModSpirits.romol, ConfigArrow.petrification.favorCost, curseColor2, ModCurses.petrification, ConfigArrow.petrification.duration);
        roots = new ArrowTalismanCurse("roots", ModSpirits.timber, ConfigArrow.roots.favorCost, curseColor2, ModCurses.roots, ConfigArrow.roots.duration);
        skyfall = new ArrowTalismanCurse("skyfall", ModSpirits.redwind, ConfigArrow.skyfall.favorCost, curseColor1, ModCurses.skyfall, ConfigArrow.skyfall.duration);
        suffocating_fumes = new ArrowTalismanCurse("suffocating_fumes", ModSpirits.timber, ConfigArrow.suffocatingFumes.favorCost, curseColor1, ModCurses.suffocating_fumes, ConfigArrow.suffocatingFumes.duration);
        wind_leash = new ArrowTalismanCurse("wind_leash", ModSpirits.redwind, ConfigArrow.windLeash.favorCost, curseColor1, ModCurses.wind_leash, ConfigArrow.windLeash.duration);
        yummy_smell = new ArrowTalismanCurse("yummy_smell", ModSpirits.timber, ConfigArrow.yummySmell.favorCost, curseColor2, ModCurses.yummy_smell, ConfigArrow.yummySmell.duration);
    }
}
