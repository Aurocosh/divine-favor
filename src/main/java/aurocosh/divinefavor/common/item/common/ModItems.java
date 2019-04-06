package aurocosh.divinefavor.common.item.common;

import aurocosh.divinefavor.common.item.*;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.bone_dagger.ItemBoneDagger;
import aurocosh.divinefavor.common.item.bone_dagger.ItemBoneDaggerAwakened;
import aurocosh.divinefavor.common.item.contract_binder.ItemContractBinder;
import aurocosh.divinefavor.common.item.talisman_container.grimoire.ItemGrimoire;
import aurocosh.divinefavor.common.item.mystic_architect_stick.ItemMysticArchitectStick;
import aurocosh.divinefavor.common.item.ritual_pouch.ItemRitualPouch;
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.ItemSpellBow;
import aurocosh.divinefavor.common.item.storage_gem.ItemStorageGem;

public final class ModItems {
    public static ModItem bone_dagger;
    public static ModItem bone_dagger_awakened;
    public static ModItem bone_key;
    public static ModItem contract_binder;
    public static ModItem grimoire;
    public static ModItem invite_gem;
    public static ModItem invite_pebble;
    public static ModItem mystic_architect_stick;
    public static ModItem milky_apple;
    public static ModItem ritual_pouch;
    public static ModItem spell_bow;
    public static ModItem stoneball;
    public static ModItem storage_gem;
    public static ModItem warp_gem;
    public static ModItem warp_pebble;

    public static void preInit() {
        bone_dagger = new ItemBoneDagger();
        bone_dagger_awakened = new ItemBoneDaggerAwakened();
        bone_key = new ItemBoneKey();
        contract_binder = new ItemContractBinder();
        grimoire = new ItemGrimoire();
        invite_gem = new ItemInviteMarker("invite_gem", true);
        invite_pebble = new ItemInviteMarker("invite_pebble", false);
        mystic_architect_stick = new ItemMysticArchitectStick();
        milky_apple = new ItemMilkyApple();
        ritual_pouch = new ItemRitualPouch();
        spell_bow = new ItemSpellBow();
        stoneball = new ItemStoneball();
        storage_gem = new ItemStorageGem();
        warp_gem = new ItemWarpMarker("warp_gem", true);
        warp_pebble = new ItemWarpMarker("warp_pebble", false);


//        ModContainer mod = Loader.instance().getModObjectList().inverse().getValue(DivineFavor.instance);
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