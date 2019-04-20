package aurocosh.divinefavor.common.potions.common;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionCharge;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggleLimited;
import aurocosh.divinefavor.common.potions.potions.*;
import net.minecraft.init.Blocks;

public class ModPotions {
    public static ModPotion consuming_fury;
    public static ModPotion crystalline_road;
    public static ModPotion empower_axe;
    public static ModPotion empower_pickaxe;
    public static ModPotion escape_plan;
    public static ModPotion fins;
    public static ModPotion focused_fury;
    public static ModPotion miners_focus;
    public static ModPotion obsidian_road;
    public static ModPotion starvation;
    public static ModPotion stone_fever;
    public static ModPotion wild_charge;
    public static ModPotion wild_sprint;
    public static ModPotion arrow_deflection;
    public static ModPotionCharge butchering_strike;
    public static ModPotionCharge fall_negation;
    public static ModPotionToggle armor_of_pacifist;
    public static ModPotionToggle gills;
    public static ModPotionToggle ground_flow;
    public static ModPotionToggle grudge;
    public static ModPotionToggle mist_blade;
    public static ModPotionToggle molten_skin;
    public static ModPotionToggle night_eye;
    public static ModPotionToggle prismatic_eyes;
    public static ModPotionToggle spider_might;
    public static ModPotionToggleLimited crushing_palm;
    public static ModPotionToggleLimited toadic_jump;
    public static ModPotionToggleLimited wooden_punch;

    public static void preInit() {
        armor_of_pacifist = new PotionArmorOfPacifist();
        arrow_deflection = new PotionArrowDeflection();
        butchering_strike = new PotionButcheringStrike();
        consuming_fury = new PotionConsumingFury();
        crushing_palm = new PotionCrushingPalm();
        crystalline_road = new PotionLiquidWalking("crystalline_road", Blocks.WATER);
        empower_axe = new PotionEmpowerAxe();
        empower_pickaxe = new PotionEmpowerPickaxe();
        escape_plan = new PotionEscapePlan();
        fall_negation = new PotionFallNegation();
        fins = new PotionFins();
        focused_fury = new PotionFocusedFury();
        gills = new PotionGills();
        ground_flow = new PotionGroundFlow();
        grudge = new PotionGrudge();
        miners_focus = new PotionMinersFocus();
        mist_blade = new PotionMistBlade();
        molten_skin = new PotionMoltenSkin();
        night_eye = new PotionNightEye();
        obsidian_road = new PotionLiquidWalking("obsidian_road", Blocks.LAVA);
        prismatic_eyes = new PotionPrismaticEyes();
        spider_might = new PotionSpiderMight();
        starvation = new PotionStarvation();
        stone_fever = new PotionStoneFever();
        toadic_jump = new PotionToadicJump();
        wild_charge = new PotionWildCharge();
        wild_sprint = new PotionWildSprint();
        wooden_punch = new PotionWoodenPunch();
    }
}
