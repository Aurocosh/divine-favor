package aurocosh.divinefavor.common.effect.common;

import aurocosh.divinefavor.common.constants.ConstEffectNames;
import aurocosh.divinefavor.common.effect.base.ModPotion;
import aurocosh.divinefavor.common.effect.base.ModPotionCharge;
import aurocosh.divinefavor.common.effect.effect.*;
import aurocosh.divinefavor.common.registry.RegistryMap;
import net.minecraft.init.Blocks;

public class ModPotions {
    public static ModPotion empower_axe;
    public static ModPotion empower_pickaxe;
    public static ModPotion lava_walk;
    public static ModPotion stone_fever;
    public static ModPotion water_walk;
    public static ModPotionCharge wooden_punch;
    public static ModPotionCharge crushing_palm;

    private static RegistryMap<ModPotion> potions = new RegistryMap<>();

    public static void preInit() {
        empower_axe = potions.register(new PotionEmpowerAxe());
        empower_pickaxe = potions.register(new PotionEmpowerPickaxe());
        lava_walk = potions.register(new PotionLiquidWalking(ConstEffectNames.LAVAWALK, Blocks.LAVA));
        stone_fever = potions.register(new PotionStoneFever());
        water_walk = potions.register(new PotionLiquidWalking(ConstEffectNames.WATERWALK, Blocks.WATER));
        wooden_punch = (ModPotionCharge) potions.register(new PotionWoodenPunch());
        crushing_palm = (ModPotionCharge) potions.register(new PotionCrushingPalm());
    }
}
