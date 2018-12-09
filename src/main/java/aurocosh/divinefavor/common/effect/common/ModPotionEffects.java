package aurocosh.divinefavor.common.effect.common;

import aurocosh.divinefavor.common.effect.base.ModPotion;
import aurocosh.divinefavor.common.effect.effect.PotionEmpowerAxe;
import aurocosh.divinefavor.common.effect.effect.PotionLavawalk;
import aurocosh.divinefavor.common.effect.effect.PotionWaterwalk;
import aurocosh.divinefavor.common.registry.CommonRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModPotionEffects {
    private static Map<ResourceLocation, ModPotion> potionMap = new HashMap<>();

    public static ModPotion waterwalk;
    public static ModPotion lavawalk;
    public static ModPotion empower_axe;

    public static Collection<ModPotion> getEffects(){
        return potionMap.values();
    }

    public static void preInit() {
        waterwalk = register(new PotionWaterwalk());
        lavawalk = register(new PotionLavawalk());
        empower_axe = register(new PotionEmpowerAxe());
    }

    private static ModPotion register(ModPotion effect) {
        effect.setIcon(effect.getIcon());
        potionMap.put(effect.getRegistryName(), effect);
        CommonRegistry.register(effect);
        return effect;
    }
}
