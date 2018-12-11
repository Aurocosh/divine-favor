package aurocosh.divinefavor.common.item.common;

import aurocosh.divinefavor.common.item.*;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones;
import aurocosh.divinefavor.common.item.ritual_pouch.ItemRitualPouch;
import aurocosh.divinefavor.common.item.talisman.ItemTalisman;
import aurocosh.divinefavor.common.item.wishing_stones.ModWishingStones;
import aurocosh.divinefavor.common.registry.CommonRegistry;
import aurocosh.divinefavor.common.talismans.ModTalismans;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class ModItems {
    private static final Map<ResourceLocation, ModItem> itemMap = new HashMap<>();

    public static ModItem bone_key;
    public static ModItem colored_bone;
    public static ModItem mystic_architect_stick;
    public static ModItem ritual_pouch;
    public static ModItem stoneball;

    public static ModItem talisman;

    public static ModItem calling_stone;
    public static ModItem wishing_stone;

    public static Collection<ModItem> getItems() {
        return itemMap.values();
    }

    public static void preInit() {
        bone_key = register(new ItemBoneKey());
        colored_bone = register(new ItemColoredBone());
        mystic_architect_stick = register(new ItemMysticArchitectStick());
        ritual_pouch = register(new ItemRitualPouch());
        stoneball = register(new ItemStoneball());

        talisman = register(new ItemTalisman("talismans/", ModTalismans.getNames()));

        calling_stone = register(new ItemCallingStone("calling_stones/", ModCallingStones.getNames()));
        wishing_stone = register(new ItemWishingStone("wishing_stones/", ModWishingStones.getNames()));

//        ModContainer mod = Loader.instance().getModObjectList().inverse().get(DivineFavor.instance);
    }

    public static void init() {
//        OreDictionary.registerOre("dustPsi", new ItemStack(material, 1, 0));
    }

//
//    private static void loadTalismans(ModContainer mod) {
//        ArrayList<String> requirementPaths = UtilAssets.getAssetPaths(mod,"talismans",".json");
//
//        RuntimeTypeAdapterFactory<Cost> costFactory = RuntimeTypeAdapterFactory
//                .of(Cost.class, "type")
//                .registerSubtype(CostFavor.class)
//                .registerSubtype(CostDayTime.class)
//                .registerSubtype(CostFree.class);
//
//        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().registerTypeAdapterFactory(costFactory).create();
//
//        ArrayList<TalismanData> talismanDataList = UtilAssets.loadObjectsFromJsonAssets(TalismanData.class,mod,requirementPaths,gson);
//        talismanDataList.forEach((data -> registerTalisman(data)));
//    }
//
//    private static void loadWishingStones(ModContainer mod) {
//        ArrayList<String> paths = UtilAssets.getAssetPaths(mod,"wishing_stones",".json");
//        Gson gson = new GsonBuilder().create();
//
//        ArrayList<WishingStoneData> dataArrayList = UtilAssets.loadObjectsFromJsonAssets(WishingStoneData.class,mod,paths,gson);
//        dataArrayList.forEach(data -> registerWishingStone(data));

//    }

    private static ModItem register(ModItem item) {
        itemMap.put(item.getRegistryName(), item);
        CommonRegistry.register(item);
        return item;
    }
}