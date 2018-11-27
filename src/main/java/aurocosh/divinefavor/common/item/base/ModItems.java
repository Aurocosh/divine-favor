package aurocosh.divinefavor.common.item.base;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.ItemStoneball;
import aurocosh.divinefavor.common.item.talisman.ItemTalisman;
import aurocosh.divinefavor.common.item.talisman.TalismanData;
import aurocosh.divinefavor.common.item.wishing_stone.ItemWishingStone;
import aurocosh.divinefavor.common.item.wishing_stone.WishingStoneData;
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
    private static Map<String, ItemMod> items = new HashMap<>();

    public static void preInit() {
        stoneball = new ItemStoneball();

        ModContainer mod = Loader.instance().getModObjectList().inverse().get(DivineFavor.instance);
        loadTalismans(mod);
        loadWishingStones(mod);
    }

    public static void init() {
        // Psi oredict mappings
//        OreDictionary.registerOre("dustPsi", new ItemStack(material, 1, 0));
//        OreDictionary.registerOre("ingotPsi", new ItemStack(material, 1, 1));
    }

    private static void loadTalismans(ModContainer mod) {
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

    private static void loadWishingStones(ModContainer mod) {
        ArrayList<String> paths = UtilAssets.getAssetPaths(mod,"wishing_stones",".json");
        Gson gson = new GsonBuilder().create();

        ArrayList<WishingStoneData> dataArrayList = UtilAssets.loadObjectsFromJsonAssets(WishingStoneData.class,mod,paths,gson);
        dataArrayList.forEach(data -> registerWishingStone(data));
    }

    public static ItemMod getTalisman(String name) {
        return items.get(name);
    }

    public static ItemMod registerTalisman(TalismanData talismanData) {
        ItemMod talisman = new ItemTalisman(talismanData);
        items.put(talismanData.name, talisman);
        return talisman;
    }

    public static ItemMod registerWishingStone(WishingStoneData stoneData) {
        ItemMod talisman = new ItemWishingStone(stoneData);
        items.put(stoneData.name, talisman);
        return talisman;
    }

    public static Map<String, ItemMod> getItems(){
        return new HashMap<>(items);
    }
}