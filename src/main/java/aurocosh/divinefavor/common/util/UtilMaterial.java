package aurocosh.divinefavor.common.util;

import net.minecraft.block.material.*;

import java.util.HashMap;

public class UtilMaterial {
    private static final HashMap<String, Material> materialMap = new HashMap<>();

    public static void registerMaterial(String name, Material material){
        materialMap.put(name, material);
    }

    public static void unregisterMaterial(String name){
        materialMap.remove(name);
    }

    public static Material getMaterial(String name){
        return materialMap.get(name);
    }

    static {
        registerMaterial("grass", Material.GRASS);
        registerMaterial("ground", Material.GROUND);
        registerMaterial("wood", Material.WOOD);
        registerMaterial("rock", Material.ROCK);
        registerMaterial("iron", Material.IRON);
        registerMaterial("anvil", Material.ANVIL);
        registerMaterial("water", Material.WATER);
        registerMaterial("lava", Material.LAVA);
        registerMaterial("leaves", Material.LEAVES);
        registerMaterial("plants", Material.PLANTS);
        registerMaterial("vine", Material.VINE);
        registerMaterial("sponge", Material.SPONGE);
        registerMaterial("fire", Material.FIRE);
        registerMaterial("sand", Material.SAND);
        registerMaterial("circuits", Material.CIRCUITS);
        registerMaterial("carpet", Material.CARPET);
        registerMaterial("glass", Material.GLASS);
        registerMaterial("redstone_light", Material.REDSTONE_LIGHT);
        registerMaterial("tnt", Material.TNT);
        registerMaterial("coral", Material.CORAL);
        registerMaterial("ice", Material.ICE);
        registerMaterial("packed_ice", Material.PACKED_ICE);
        registerMaterial("snow", Material.SNOW);
        registerMaterial("crafted_snow", Material.CRAFTED_SNOW);
        registerMaterial("cactus", Material.CACTUS);
        registerMaterial("clay", Material.CLAY);
        registerMaterial("gourd", Material.GOURD);
        registerMaterial("dragon_egg", Material.DRAGON_EGG);
        registerMaterial("cake", Material.CAKE);
        registerMaterial("web", Material.WEB);
    }
}
