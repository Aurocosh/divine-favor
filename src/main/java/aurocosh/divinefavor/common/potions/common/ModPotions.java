package aurocosh.divinefavor.common.potions.common;

import aurocosh.divinefavor.common.constants.ConstEffectNames;
import aurocosh.divinefavor.common.potions.base.ModPotion;
import aurocosh.divinefavor.common.potions.base.ModPotionCharge;
import aurocosh.divinefavor.common.potions.effect.*;
import aurocosh.divinefavor.common.registry.ModRegistries;
import net.minecraft.init.Blocks;

public class ModPotions {
    public static ModPotion empower_axe;
    public static ModPotion empower_pickaxe;
    public static ModPotion lava_walk;
    public static ModPotion stone_fever;
    public static ModPotion water_walk;
    public static ModPotionCharge wooden_punch;
    public static ModPotionCharge crushing_palm;

    public static void preInit() {
        empower_axe = ModRegistries.potions.register(new PotionEmpowerAxe());
        empower_pickaxe = ModRegistries.potions.register(new PotionEmpowerPickaxe());
        lava_walk = ModRegistries.potions.register(new PotionLiquidWalking(ConstEffectNames.LAVAWALK, Blocks.LAVA));
        stone_fever = ModRegistries.potions.register(new PotionStoneFever());
        water_walk = ModRegistries.potions.register(new PotionLiquidWalking(ConstEffectNames.WATERWALK, Blocks.WATER));
        wooden_punch = ModRegistries.potions.register(new PotionWoodenPunch());
        crushing_palm = ModRegistries.potions.register(new PotionCrushingPalm());
    }
}