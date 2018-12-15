package aurocosh.divinefavor.common.potions.common;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionCharge;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionTrigger;
import aurocosh.divinefavor.common.potions.potions.*;
import aurocosh.divinefavor.common.registry.ModRegistries;
import net.minecraft.init.Blocks;

public class ModPotions {
    public static ModPotion empower_axe;
    public static ModPotion empower_pickaxe;
    public static ModPotion obsidian_road;
    public static ModPotion stone_fever;
    public static ModPotion crystalline_road;
    public static ModPotionCharge butchering_strike;
    public static ModPotionCharge crushing_palm;
    public static ModPotionCharge wooden_punch;
    public static ModPotionToggle grudge;
    public static ModPotionTrigger consuming_fury;
    public static ModPotionTrigger focused_fury;
    public static ModPotionTrigger miners_focus;

    public static void preInit() {
        butchering_strike = ModRegistries.potions.register(new PotionButcheringStrike());
        consuming_fury = ModRegistries.potions.register(new PotionConsumingFury());
        crushing_palm = ModRegistries.potions.register(new PotionCrushingPalm());
        empower_axe = ModRegistries.potions.register(new PotionEmpowerAxe());
        empower_pickaxe = ModRegistries.potions.register(new PotionEmpowerPickaxe());
        focused_fury = ModRegistries.potions.register(new PotionFocusedFury());
        grudge = ModRegistries.potions.register(new PotionGrudge());
        obsidian_road = ModRegistries.potions.register(new PotionLiquidWalking("obsidian_road", Blocks.LAVA));
        miners_focus = ModRegistries.potions.register(new PotionMinersFocus());
        stone_fever = ModRegistries.potions.register(new PotionStoneFever());
        crystalline_road = ModRegistries.potions.register(new PotionLiquidWalking("crystalline_road", Blocks.WATER));
        wooden_punch = ModRegistries.potions.register(new PotionWoodenPunch());
    }
}
