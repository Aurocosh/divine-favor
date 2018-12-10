package aurocosh.divinefavor.common.favors;

import aurocosh.divinefavor.common.constants.ConstFavorType;
import aurocosh.divinefavor.common.registry.CommonRegistry;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public final class ModFavors {
    private static final Map<ResourceLocation, ModFavor> favors = new HashMap<>();
    public static ModFavor favor_of_allfire;
    public static ModFavor favor_of_timber;
    public static ModFavor favor_of_romol;

    private static int nextId = 0;

    public static void preInit() {
        favor_of_allfire = register(new ModFavor(ConstFavorType.FAVOR_OF_ALLFIRE, nextId++));
        favor_of_timber = register(new ModFavor(ConstFavorType.FAVOR_OF_TIMBER, nextId++));
        favor_of_romol = register(new ModFavor(ConstFavorType.FAVOR_OF_ROMOL, nextId++));
    }

    public static Collection<ModFavor> getFavorList() {
        return favors.values();
    }

    private static ModFavor register(ModFavor favor) {
        favors.put(favor.getRegistryName(), favor);
        CommonRegistry.register(favor);
        return favor;
    }
}