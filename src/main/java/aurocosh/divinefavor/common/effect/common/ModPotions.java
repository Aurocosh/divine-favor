package aurocosh.divinefavor.common.effect.common;

import aurocosh.divinefavor.common.constants.ConstEffectNames;
import aurocosh.divinefavor.common.effect.base.ModPotion;
import aurocosh.divinefavor.common.effect.base.ModPotionCharge;
import aurocosh.divinefavor.common.effect.effect.PotionEmpowerAxe;
import aurocosh.divinefavor.common.effect.effect.PotionLiquidWalking;
import aurocosh.divinefavor.common.effect.effect.PotionStoneFever;
import aurocosh.divinefavor.common.effect.effect.PotionWoodenPunch;
import aurocosh.divinefavor.common.registry.RegistryMap;
import aurocosh.divinefavor.common.registry.common.CommonRegistry;
import net.minecraft.init.Blocks;

import java.util.Collection;
import java.util.HashMap;

public class ModPotions {
    public static ModPotion empower_axe;
    public static ModPotion lava_walk;
    public static ModPotion stone_fever;
    public static ModPotion water_walk;
    public static ModPotionCharge wooden_punch;

    private static RegistryMap<ModPotion> potions = new RegistryMap<>();

    public static void preInit() {
        empower_axe = potions.register(new PotionEmpowerAxe());
        lava_walk = potions.register(new PotionLiquidWalking(ConstEffectNames.LAVAWALK, Blocks.LAVA));
        stone_fever = potions.register(new PotionStoneFever());
        water_walk = potions.register(new PotionLiquidWalking(ConstEffectNames.WATERWALK, Blocks.WATER));
        wooden_punch = (ModPotionCharge) potions.register(new PotionWoodenPunch());
    }
}
