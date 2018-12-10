package aurocosh.divinefavor.common.item.common;

import aurocosh.divinefavor.common.constants.items.ConstItemNames;
import aurocosh.divinefavor.common.constants.items.LibCallingStoneNames;
import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.item.ItemStoneball;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.ItemBoneKey;
import aurocosh.divinefavor.common.item.ItemCallingStone;
import aurocosh.divinefavor.common.item.ItemMysticArchitectStick;
import aurocosh.divinefavor.common.item.ritual_pouch.ItemRitualPouch;
import aurocosh.divinefavor.common.item.talisman.TalismanBuilder;
import aurocosh.divinefavor.common.item.ItemWishingStone;
import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks;
import aurocosh.divinefavor.common.registry.CommonRegistry;
import aurocosh.divinefavor.common.spell.common.ModSpells;
import aurocosh.divinefavor.common.spirit.ModSpirits;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class ModItems {
    private static final Map<ResourceLocation, ModItem> itemMap = new HashMap<>();

    public static ModItem bone_key;
    public static ModItem mystic_architect_stick;
    public static ModItem ritual_pouch;
    public static ModItem stoneball;

    public static ModItem arrowThrowTalisman;
    public static ModItem bonemeal_talisman;
    public static ModItem empower_axe_talisman;
    public static ModItem fell_tree_talisman;
    public static ModItem ignition_talisman;
    public static ModItem lavawalking_talisman;
    public static ModItem snowball_throw_talisman;
    public static ModItem stoneball_throw_talisman;
    public static ModItem small_fireball_throw_talisman;
    public static ModItem waterwalking_talisman;
    public static ModItem wooden_punch_talisman;


    public static ModItem allfire_calling_stone;
    public static ModItem timber_calling_stone;

    public static ModItem allfire_wishing_stone;
    public static ModItem timber_wishing_stone;

    public static Collection<ModItem> getItems() {
        return itemMap.values();
    }

    public static void preInit() {
        bone_key = register(new ItemBoneKey());
        mystic_architect_stick = register(new ItemMysticArchitectStick());
        ritual_pouch = register(new ItemRitualPouch());
        stoneball = register(new ItemStoneball());

        generateTalismans();
        generateCallingStones();
        generateWishingStones();

//        ModContainer mod = Loader.instance().getModObjectList().inverse().get(DivineFavor.instance);
    }

    public static void init() {
//        OreDictionary.registerOre("dustPsi", new ItemStack(material, 1, 0));
//        OreDictionary.registerOre("ingotPsi", new ItemStack(material, 1, 1));
    }

    public static void generateTalismans() {
        arrowThrowTalisman = register(new TalismanBuilder(ConstItemNames.ARROW_THROW_TALISMAN, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.arrow_throw)
                .castOnUse()
                .castOnRighClick()
                .create());
        bonemeal_talisman = register(new TalismanBuilder(ConstItemNames.BONEMEAL_TALISMAN, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.bonemeal)
                .castOnUse()
                .create());
        empower_axe_talisman = register(new TalismanBuilder(ConstItemNames.EMPOWER_AXE_TALISMAN, ModFavors.favor_of_timber, 1)
                .setSpell(ModSpells.empower_axe)
                .castOnUse()
                .castOnRighClick()
                .create());
        fell_tree_talisman = register(new TalismanBuilder(ConstItemNames.FELL_TREE_TALISMAN, ModFavors.favor_of_timber, 1)
                .setSpell(ModSpells.fell_tree)
                .castOnUse()
                .create());
        ignition_talisman = register(new TalismanBuilder(ConstItemNames.IGNITION_TALISMAN, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.ignition)
                .castOnUse()
                .create());
        lavawalking_talisman = register(new TalismanBuilder(ConstItemNames.LAVAWALKING_TALISMAN, ModFavors.favor_of_allfire, 2)
                .setSpell(ModSpells.lavawalking)
                .castOnUse()
                .castOnRighClick()
                .create());
        snowball_throw_talisman = register(new TalismanBuilder(ConstItemNames.SNOWBALL_THROW_TALISMAN, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.snowball_throw)
                .castOnUse()
                .castOnRighClick()
                .create());
        stoneball_throw_talisman = register(new TalismanBuilder(ConstItemNames.STONEBALL_THROW_TALISMAN, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.stoneball_throw)
                .castOnUse()
                .castOnRighClick()
                .create());
        small_fireball_throw_talisman = register(new TalismanBuilder(ConstItemNames.SMALL_FIREBALL_THROW_TALISMAN, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.small_fireball_throw)
                .castOnRighClick()
                .create());
        waterwalking_talisman = register(new TalismanBuilder(ConstItemNames.WATERWALKING_TALISMAN, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.waterwalking)
                .castOnUse()
                .castOnRighClick()
                .create());
        wooden_punch_talisman = register(new TalismanBuilder(ConstItemNames.WOODEN_PUNCH_TALISMAN, ModFavors.favor_of_timber, 2)
                .setSpell(ModSpells.wooden_punch)
                .castOnUse()
                .castOnRighClick()
                .create());
    }

    private static void generateCallingStones() {
        allfire_calling_stone = register(new ItemCallingStone(LibCallingStoneNames.ALLFIRE_CALLING_STONE, ModSpirits.allfire, ModMultiBlocks.allfire_altar));
        timber_calling_stone = register(new ItemCallingStone(LibCallingStoneNames.TIMBER_CALLING_STONE, ModSpirits.timber, ModMultiBlocks.timber_altar));
    }
    private static void generateWishingStones() {
        allfire_wishing_stone = register(new ItemWishingStone("allfire_wishing_stone", ModFavors.favor_of_allfire, 1));
        timber_wishing_stone = register(new ItemWishingStone("timber_wishing_stone", ModFavors.favor_of_timber, 1));
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