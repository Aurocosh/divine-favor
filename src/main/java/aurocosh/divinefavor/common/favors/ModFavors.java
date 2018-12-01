package aurocosh.divinefavor.common.favors;

import aurocosh.divinefavor.common.registry.FavorRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModFavors {
    private static int nextId = 0;
    private static List<ModFavor> favors = new ArrayList<>();
    private static List<Integer> favorsIds = new ArrayList<>();

    public static ModFavor favor_of_allfire;
    public static ModFavor favor_of_timber;

    public static void preInit() {
        favor_of_allfire = register("favor_of_allfire","favor_of_allfire");
        favor_of_timber = register("favor_of_timber","favor_of_timber");
    }

    public static List<ModFavor> getFavorList(){return Collections.unmodifiableList(favors);}
    public static List<Integer> getFavorIds(){return Collections.unmodifiableList(favorsIds);}

    public static ModFavor register(String name, String tag) {
        ModFavor favor = new ModFavor(name,tag,nextId++);
        favors.add(favor);
        FavorRegistry.register(favor);
        return favor;
    }
}