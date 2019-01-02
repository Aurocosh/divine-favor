package aurocosh.divinefavor.common.potions.common;

import aurocosh.divinefavor.common.potions.base.potion.*;
import aurocosh.divinefavor.common.potions.potions.*;
import aurocosh.divinefavor.common.registry.ModRegistries;
import net.minecraft.init.Blocks;

public class ModPotions {
    public static ModPotion crystalline_road;
    public static ModPotion empower_axe;
    public static ModPotion empower_pickaxe;
    public static ModPotion mist_blade;
    public static ModPotion night_eye;
    public static ModPotion obsidian_road;
    public static ModPotion stone_fever;
    public static ModPotion wild_sprint;
    public static ModPotionCharge butchering_strike;
    public static ModPotionCharge crushing_palm;
    public static ModPotionCharge fall_negation;
    public static ModPotionCharge toadic_jump;
    public static ModPotionToggle ground_flow;
    public static ModPotionToggle grudge;
    public static ModPotionToggle molten_skin;
    public static ModPotionToggleLimited wooden_punch;
    public static ModPotionTrigger consuming_fury;
    public static ModPotionTrigger focused_fury;
    public static ModPotionTrigger miners_focus;
    public static ModPotionTrigger wild_charge;

    public static void preInit() {
        butchering_strike = ModRegistries.potions.register(new PotionButcheringStrike());
        consuming_fury = ModRegistries.potions.register(new PotionConsumingFury());
        crushing_palm = ModRegistries.potions.register(new PotionCrushingPalm());
        crystalline_road = ModRegistries.potions.register(new PotionLiquidWalking("crystalline_road", Blocks.WATER));
        empower_axe = ModRegistries.potions.register(new PotionEmpowerAxe());
        empower_pickaxe = ModRegistries.potions.register(new PotionEmpowerPickaxe());
        fall_negation = ModRegistries.potions.register(new PotionFallNegation());
        focused_fury = ModRegistries.potions.register(new PotionFocusedFury());
        ground_flow = ModRegistries.potions.register(new PotionGroundFlow());
        grudge = ModRegistries.potions.register(new PotionGrudge());
        miners_focus = ModRegistries.potions.register(new PotionMinersFocus());
        mist_blade = ModRegistries.potions.register(new PotionMistBlade());
        molten_skin = ModRegistries.potions.register(new PotionMoltenSkin());
        night_eye = ModRegistries.potions.register(new PotionNightEye());
        obsidian_road = ModRegistries.potions.register(new PotionLiquidWalking("obsidian_road", Blocks.LAVA));
        stone_fever = ModRegistries.potions.register(new PotionStoneFever());
        toadic_jump = ModRegistries.potions.register(new PotionToadicJump());
        wild_charge = ModRegistries.potions.register(new PotionWildCharge());
        wild_sprint = ModRegistries.potions.register(new PotionWildSprint());
        wooden_punch = ModRegistries.potions.register(new PotionWoodenPunch());
    }
}
