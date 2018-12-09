package aurocosh.divinefavor.common.favors;

import aurocosh.divinefavor.common.registry.base.CommonRegistry;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public final class ModFavors {
    private static final Map<ResourceLocation, ModFavor> favors = new HashMap<>();
    public static ModFavor favor_of_allfire;
    public static ModFavor favor_of_timber;
    private static List<Integer> favorsIds = new ArrayList<>();
    private static int nextId = 0;

    public static void preInit() {
        favor_of_allfire = register(new ModFavor("allfire", "allfire", nextId++));
        favor_of_timber = register(new ModFavor("timber", "timber", nextId++));
    }

    public static Collection<ModFavor> getFavorList() {
        return favors.values();
    }

    public static List<Integer> getFavorIds() {
        return Collections.unmodifiableList(favorsIds);
    }

    private static ModFavor register(ModFavor favor) {
        favors.put(favor.getRegistryName(), favor);
        CommonRegistry.register(favor);
        return favor;
    }
}