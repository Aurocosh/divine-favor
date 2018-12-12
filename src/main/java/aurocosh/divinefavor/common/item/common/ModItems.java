package aurocosh.divinefavor.common.item.common;

import aurocosh.divinefavor.common.item.*;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.calling_stones.CallingStone;
import aurocosh.divinefavor.common.item.calling_stones.ItemCallingStone;
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones;
import aurocosh.divinefavor.common.item.ritual_pouch.ItemRitualPouch;
import aurocosh.divinefavor.common.item.talismans.ItemTalisman;
import aurocosh.divinefavor.common.item.wishing_stones.ItemWishingStone;
import aurocosh.divinefavor.common.item.wishing_stones.ModWishingStones;
import aurocosh.divinefavor.common.item.wishing_stones.WishingStone;
import aurocosh.divinefavor.common.registry.RegistryMap;
import aurocosh.divinefavor.common.item.talismans.ModTalismans;
import aurocosh.divinefavor.common.item.talismans.Talisman;
import net.minecraft.item.ItemStack;

import java.util.Collection;

public final class ModItems {
    private static final RegistryMap<ModItem> items = new RegistryMap<>();

    public static ModItem bone_key;
    public static ModItem colored_bone;
    public static ModItem mystic_architect_stick;
    public static ModItem ritual_pouch;
    public static ModItem stoneball;

    public static ModItem talisman;

    public static ModItem calling_stone;
    public static ModItem wishing_stone;

    public static Collection<ModItem> getItems() {
        return items.getValues();
    }

    public static ItemStack getCallingStone(CallingStone variant) {
        return new ItemStack(calling_stone, 1, ModCallingStones.getMetaContainer().getMeta(variant));
    }

    public static ItemStack getTalisman(Talisman variant) {
        return new ItemStack(talisman, 1, ModTalismans.getMetaContainer().getMeta(variant));
    }

    public static ItemStack getWishingStone(WishingStone variant) {
        return new ItemStack(wishing_stone, 1, ModWishingStones.getMetaContainer().getMeta(variant));
    }

    public static void preInit() {
        bone_key = items.register(new ItemBoneKey());
        colored_bone = items.register(new ItemColoredBone());
        mystic_architect_stick = items.register(new ItemMysticArchitectStick());
        ritual_pouch = items.register(new ItemRitualPouch());
        stoneball = items.register(new ItemStoneball());

        talisman = items.register(new ItemTalisman("talismans/", ModTalismans.getMetaContainer().getNames()));

        calling_stone = items.register(new ItemCallingStone("calling_stones/", ModCallingStones.getMetaContainer().getNames()));
        wishing_stone = items.register(new ItemWishingStone("wishing_stones/", ModWishingStones.getMetaContainer().getNames()));

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
}