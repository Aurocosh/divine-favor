package aurocosh.divinefavor.common.item.base;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.ItemStoneball;
import aurocosh.divinefavor.common.item.ItemTalisman;
import aurocosh.divinefavor.common.lib.RuntimeTypeAdapterFactory;
import aurocosh.divinefavor.common.requirements.requirement.Cost;
import aurocosh.divinefavor.common.requirements.requirement.CostFavor;
import aurocosh.divinefavor.common.requirements.requirement.CostFree;
import aurocosh.divinefavor.common.requirements.requirement.CostMultipleOptions;
import aurocosh.divinefavor.common.util.UtilAssets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import vazkii.arl.item.ItemMod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class ModItems {
    public static ItemMod stoneball;
    private static Map<String, ItemMod> talismans = new HashMap<>();

    public static void preInit() {
        stoneball = new ItemStoneball();

        ModContainer mod = Loader.instance().getModObjectList().inverse().get(DivineFavor.instance);
        ArrayList<String> requirementPaths = UtilAssets.getAssetPaths(mod,"talismans",".json");

        RuntimeTypeAdapterFactory<Cost> costFactory = RuntimeTypeAdapterFactory
                .of(Cost.class, "type")
                .registerSubtype(CostFavor.class)
                .registerSubtype(CostFree.class)
                .registerSubtype(CostMultipleOptions.class);

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().registerTypeAdapterFactory(costFactory).create();

        ArrayList<TalismanData> talismanDataList = UtilAssets.loadObjectsFromJsonAssets(TalismanData.class,mod,requirementPaths,gson);
        talismanDataList.forEach((data -> registerTalisman(data)));
    }

    public static void init() {
        // Psi oredict mappings
//        OreDictionary.registerOre("dustPsi", new ItemStack(material, 1, 0));
//        OreDictionary.registerOre("ingotPsi", new ItemStack(material, 1, 1));
    }

    public static ItemMod getTalisman(String name) {
        return talismans.get(name);
    }

    public static ItemMod registerTalisman(TalismanData talismanData) {
        ItemMod talisman = new ItemTalisman(talismanData);
        talismans.put(talismanData.name, talisman);
        return talisman;
    }

    public static Map<String, ItemMod> getTalismans(){
        return new HashMap<>(talismans);
    }
}