package aurocosh.divinefavor.common.favors;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.base.TalismanData;
import aurocosh.divinefavor.common.lib.RuntimeTypeAdapterFactory;
import aurocosh.divinefavor.common.registry.FavorRegistry;
import aurocosh.divinefavor.common.registry.SpellRegestry;
import aurocosh.divinefavor.common.requirements.requirement.Cost;
import aurocosh.divinefavor.common.requirements.requirement.CostFavor;
import aurocosh.divinefavor.common.requirements.requirement.CostFree;
import aurocosh.divinefavor.common.requirements.requirement.CostMultipleOptions;
import aurocosh.divinefavor.common.util.UtilAssets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class ModFavors {
    private static int nextId = 0;
    private static ArrayList<ModFavor> favors = new ArrayList<>();
    private static ArrayList<Integer> favorsIds = new ArrayList<>();
    private static Map<String, ModFavor> favorsByName = new HashMap<>();
    private static Map<Integer, ModFavor> favorsById = new HashMap<>();

    public static void preInit() {
        ModContainer mod = Loader.instance().getModObjectList().inverse().get(DivineFavor.instance);
        ArrayList<String> favorPaths = UtilAssets.getAssetPaths(mod,"favors",".json");

        Gson gson = new GsonBuilder().create();

        ArrayList<FavorData> favorDataList = UtilAssets.loadObjectsFromJsonAssets(FavorData.class,mod,favorPaths,gson);
        favorDataList.forEach((data -> register(data)));
    }

    public static ModFavor getByName(String name) {
        return favorsByName.get(name);
    }
    public static ModFavor getById(int id) {
        return favorsById.get(id);
    }

    public static ArrayList<ModFavor> getFavorList(){return new ArrayList<>(favors);}
    public static ArrayList<Integer> getFavorIds(){return new ArrayList<>(favorsIds);}

    public static ModFavor register(FavorData favorData) {
        ModFavor favor = new ModFavor(favorData.name,favorData.tag,nextId++);
        favors.add(favor);
        favorsByName.put(favor.getName(), favor);
        favorsById.put(favor.getId(), favor);
        FavorRegistry.register(favor);
        return favor;
    }
}