package aurocosh.divinefavor.common.effect.common;

import aurocosh.divinefavor.common.constants.ConstEffectNames;
import aurocosh.divinefavor.common.effect.base.ModPotion;
import aurocosh.divinefavor.common.effect.effect.PotionEmpowerAxe;
import aurocosh.divinefavor.common.effect.effect.PotionLiquidWalking;
import aurocosh.divinefavor.common.registry.CommonRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModPotions {
    public static ModPotion water_walk;
    public static ModPotion lava_walk;
    public static ModPotion empower_axe;
    private static Map<ResourceLocation, ModPotion> potionMap = new HashMap<>();

    public static Collection<ModPotion> getPotions() {
        return potionMap.values();
    }

    public static void preInit() {
        water_walk = register(new PotionLiquidWalking(ConstEffectNames.WATERWALK, Blocks.WATER));
        lava_walk = register(new PotionLiquidWalking(ConstEffectNames.LAVAWALK, Blocks.LAVA));
        empower_axe = register(new PotionEmpowerAxe());
    }

    private static ModPotion register(ModPotion effect) {
        effect.setIcon(effect.getIcon());
        potionMap.put(effect.getRegistryName(), effect);
        CommonRegistry.register(effect);
        return effect;
    }
}
