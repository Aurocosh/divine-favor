package aurocosh.divinefavor.common.favors;

import aurocosh.divinefavor.common.constants.ConstFavorType;
import aurocosh.divinefavor.common.registry.RegistryMap;
import aurocosh.divinefavor.common.registry.common.CommonRegistry;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public final class ModFavors {
    private static final RegistryMap<ModFavor> favors = new RegistryMap<>();

    public static ModFavor favor_of_allfire;
    public static ModFavor favor_of_timber;
    public static ModFavor favor_of_romol;

    private static int nextId = 0;

    public static Collection<ModFavor> getFavorList() {
        return favors.getValues();
    }

    public static void preInit() {
        favor_of_allfire = favors.register(new ModFavor(ConstFavorType.FAVOR_OF_ALLFIRE, nextId++));
        favor_of_timber = favors.register(new ModFavor(ConstFavorType.FAVOR_OF_TIMBER, nextId++));
        favor_of_romol = favors.register(new ModFavor(ConstFavorType.FAVOR_OF_ROMOL, nextId++));
    }
}