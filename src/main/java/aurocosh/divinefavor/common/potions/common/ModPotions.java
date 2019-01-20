package aurocosh.divinefavor.common.potions.common;

import aurocosh.divinefavor.common.potions.base.potion.*;
import aurocosh.divinefavor.common.potions.potions.*;
import aurocosh.divinefavor.common.registry.ModRegistries;
import net.minecraft.init.Blocks;

public class ModPotions {
    public static ModPotion crystalline_road;
    public static ModPotion empower_axe;
    public static ModPotion empower_pickaxe;
    public static ModPotion escape_plan;
    public static ModPotion fins;
    public static ModPotion obsidian_road;
    public static ModPotion roots;
    public static ModPotion skybound;
    public static ModPotion spider_might;
    public static ModPotion starvation;
    public static ModPotion stone_fever;
    public static ModPotion wild_sprint;
    public static ModPotionCharge butchering_strike;
    public static ModPotionCharge fall_negation;
    public static ModPotionToggle armor_of_pacifist;
    public static ModPotionToggle gills;
    public static ModPotionToggle ground_flow;
    public static ModPotionToggle grudge;
    public static ModPotionToggle mist_blade;
    public static ModPotionToggle molten_skin;
    public static ModPotionToggle night_eye;
    public static ModPotionToggleLimited crushing_palm;
    public static ModPotionToggleLimited toadic_jump;
    public static ModPotionToggleLimited wooden_punch;
    public static ModPotionTrigger consuming_fury;
    public static ModPotionTrigger focused_fury;
    public static ModPotionTrigger fiery_mark;
    public static ModPotionTrigger miners_focus;
    public static ModPotionTrigger wild_charge;

    public static void preInit() {
        armor_of_pacifist = ModRegistries.potions.register(new PotionArmorOfPacifist());
        butchering_strike = ModRegistries.potions.register(new PotionButcheringStrike());
        consuming_fury = ModRegistries.potions.register(new PotionConsumingFury());
        crushing_palm = ModRegistries.potions.register(new PotionCrushingPalm());
        crystalline_road = ModRegistries.potions.register(new PotionLiquidWalking("crystalline_road", Blocks.WATER));
        empower_axe = ModRegistries.potions.register(new PotionEmpowerAxe());
        empower_pickaxe = ModRegistries.potions.register(new PotionEmpowerPickaxe());
        escape_plan = ModRegistries.potions.register(new PotionEscapePlan());
        fall_negation = ModRegistries.potions.register(new PotionFallNegation());
        fiery_mark = ModRegistries.potions.register(new PotionFieryMark());
        fins = ModRegistries.potions.register(new PotionFins());
        focused_fury = ModRegistries.potions.register(new PotionFocusedFury());
        gills = ModRegistries.potions.register(new PotionGills());
        ground_flow = ModRegistries.potions.register(new PotionGroundFlow());
        grudge = ModRegistries.potions.register(new PotionGrudge());
        miners_focus = ModRegistries.potions.register(new PotionMinersFocus());
        mist_blade = ModRegistries.potions.register(new PotionMistBlade());
        molten_skin = ModRegistries.potions.register(new PotionMoltenSkin());
        night_eye = ModRegistries.potions.register(new PotionNightEye());
        obsidian_road = ModRegistries.potions.register(new PotionLiquidWalking("obsidian_road", Blocks.LAVA));
        roots = ModRegistries.potions.register(new PotionRoots());
        skybound = ModRegistries.potions.register(new PotionSkybound());
        spider_might = ModRegistries.potions.register(new PotionSpiderMight());
        starvation = ModRegistries.potions.register(new PotionStarvation());
        stone_fever = ModRegistries.potions.register(new PotionStoneFever());
        toadic_jump = ModRegistries.potions.register(new PotionToadicJump());
        wild_charge = ModRegistries.potions.register(new PotionWildCharge());
        wild_sprint = ModRegistries.potions.register(new PotionWildSprint());
        wooden_punch = ModRegistries.potions.register(new PotionWoodenPunch());
    }
}
