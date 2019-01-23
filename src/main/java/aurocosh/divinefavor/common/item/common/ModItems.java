package aurocosh.divinefavor.common.item.common;

import aurocosh.divinefavor.common.item.*;
import aurocosh.divinefavor.common.item.mystic_architect_stick.ItemMysticArchitectStick;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.contract_binder.ItemContractBinder;
import aurocosh.divinefavor.common.item.grimoire.ItemGrimoire;
import aurocosh.divinefavor.common.item.storage_gem.ItemStorageGem;
import aurocosh.divinefavor.common.item.ritual_pouch.ItemRitualPouch;
import aurocosh.divinefavor.common.item.spell_bow.ItemSpellBow;
import aurocosh.divinefavor.common.registry.ModRegistries;

public final class ModItems {
    public static ModItem bone_key;
    public static ModItem contract_binder;
    public static ModItem grimoire;
    public static ModItem invite_gem;
    public static ModItem invite_pebble;
    public static ModItem mystic_architect_stick;
    public static ModItem pure_apple;
    public static ModItem ritual_pouch;
    public static ModItem spell_bow;
    public static ModItem stoneball;
    public static ModItem storage_gem;
    public static ModItem warp_gem;
    public static ModItem warp_pebble;

    public static void preInit() {
        bone_key = ModRegistries.items.register(new ItemBoneKey());
        contract_binder = ModRegistries.items.register(new ItemContractBinder());
        grimoire = ModRegistries.items.register(new ItemGrimoire());
        invite_gem = ModRegistries.items.register(new ItemInviteMarker("invite_gem", true));
        invite_pebble = ModRegistries.items.register(new ItemInviteMarker("invite_pebble", false));
        mystic_architect_stick = ModRegistries.items.register(new ItemMysticArchitectStick());
        pure_apple = ModRegistries.items.register(new ItemPureApple());
        ritual_pouch = ModRegistries.items.register(new ItemRitualPouch());
        spell_bow = ModRegistries.items.register(new ItemSpellBow());
        stoneball = ModRegistries.items.register(new ItemStoneball());
        storage_gem = ModRegistries.items.register(new ItemStorageGem());
        warp_gem = ModRegistries.items.register(new ItemWarpMarker("warp_gem", true));
        warp_pebble = ModRegistries.items.register(new ItemWarpMarker("warp_pebble", false));


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