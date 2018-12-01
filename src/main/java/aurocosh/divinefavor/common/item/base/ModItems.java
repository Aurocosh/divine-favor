package aurocosh.divinefavor.common.item.base;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.LibItemNames;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.item.ItemStoneball;
import aurocosh.divinefavor.common.item.ritual_pouch.RitualPouch;
import aurocosh.divinefavor.common.item.talisman.ItemTalisman;
import aurocosh.divinefavor.common.item.talisman.TalismanBuilder;
import aurocosh.divinefavor.common.item.wishing_stone.ItemWishingStone;
import aurocosh.divinefavor.common.item.wishing_stone.WishingStoneData;
import aurocosh.divinefavor.common.requirements.cost.costs.CostFavor;
import aurocosh.divinefavor.common.spell.base.ModSpells;
import aurocosh.divinefavor.common.util.UtilAssets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import vazkii.arl.item.ItemMod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModItems {
    private static List<ItemMod> items = new ArrayList<>();

    private static List<ItemTalisman> talismans = new ArrayList<>();
    private static List<ItemWishingStone> wishingStones = new ArrayList<>();


    public static ItemMod stoneball;
    public static ItemMod ritual_pouch;

    public static ItemMod arrowThrowTalisman;
    public static ItemMod bonemeal_talisman;
    public static ItemMod empower_axe_talisman;
    public static ItemMod fell_tree_talisman;
    public static ItemMod ignition_talisman;
    public static ItemMod lavawalking_talisman;
    public static ItemMod snowball_throw_talisman;
    public static ItemMod stoneball_throw_talisman;
    public static ItemMod small_fireball_throw_talisman;
    public static ItemMod waterwalking_talisman;

    public static ItemMod allfire_wishing_stone;
    public static ItemMod timber_wishing_stone;

    public static void preInit() {
        stoneball = registerItem(new ItemStoneball());
        ritual_pouch = registerItem(new RitualPouch());
        generateTalismans();
        generateWishingStones();

//        ModContainer mod = Loader.instance().getModObjectList().inverse().get(DivineFavor.instance);
    }

    public static void init() {
        // Psi oredict mappings
//        OreDictionary.registerOre("dustPsi", new ItemStack(material, 1, 0));
//        OreDictionary.registerOre("ingotPsi", new ItemStack(material, 1, 1));
    }

    public static void generateTalismans() {
        arrowThrowTalisman = registerTalisman( new TalismanBuilder(LibItemNames.ARROW_THROW_TALISMAN)
                .setSpell(ModSpells.arrow_throw)
                .castOnUse()
                .castOnRighClick()
                .addCost(new CostFavor(ModFavors.favor_of_allfire,1))
                .create());
        bonemeal_talisman = registerTalisman( new TalismanBuilder(LibItemNames.BONEMEAL_TALISMAN)
                .setSpell(ModSpells.bonemeal)
                .castOnUse()
                .create());
        empower_axe_talisman = registerTalisman( new TalismanBuilder(LibItemNames.EMPOWER_AXE_TALISMAN)
                .setSpell(ModSpells.empower_axe)
                .castOnUse()
                .castOnRighClick()
                .create());
        fell_tree_talisman = registerTalisman( new TalismanBuilder(LibItemNames.FELL_TREE_TALISMAN)
                .setSpell(ModSpells.fell_tree)
                .castOnUse()
                .create());
        ignition_talisman = registerTalisman( new TalismanBuilder(LibItemNames.IGNITION_TALISMAN)
                .setSpell(ModSpells.ignition)
                .castOnUse()
                .create());
        lavawalking_talisman = registerTalisman( new TalismanBuilder(LibItemNames.LAVAWALKING_TALISMAN)
                .setSpell(ModSpells.lavawalking)
                .castOnUse()
                .castOnRighClick()
                .create());
        snowball_throw_talisman = registerTalisman( new TalismanBuilder(LibItemNames.SNOWBALL_THROW_TALISMAN)
                .setSpell(ModSpells.snowball_throw)
                .castOnUse()
                .castOnRighClick()
                .create());
        stoneball_throw_talisman = registerTalisman( new TalismanBuilder(LibItemNames.STONEBALL_THROW_TALISMAN)
                .setSpell(ModSpells.stoneball_throw)
                .castOnUse()
                .castOnRighClick()
                .create());
        small_fireball_throw_talisman = registerTalisman( new TalismanBuilder(LibItemNames.SMALL_FIREBALL_THROW_TALISMAN)
                .setSpell(ModSpells.small_fireball_throw)
                .castOnRighClick()
                .create());
        waterwalking_talisman = registerTalisman( new TalismanBuilder(LibItemNames.WATERWALKING_TALISMAN)
                .setSpell(ModSpells.waterwalking)
                .castOnUse()
                .castOnRighClick()
                .create());
    }

    private static void generateWishingStones(){
        allfire_wishing_stone = registerWishingStone("allfire_wishing_stone",ModFavors.favor_of_allfire,1);
        timber_wishing_stone = registerWishingStone("timber_wishing_stone",ModFavors.favor_of_timber,1);
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

    public static ItemMod registerTalisman(ItemTalisman talisman) {
        items.add(talisman);
        talismans.add(talisman);
        return talisman;
    }

    public static ItemMod registerWishingStone(String name, ModFavor favor, int favorCount) {
        ItemWishingStone talisman = new ItemWishingStone(name,favor,favorCount);
        items.add(talisman);
        wishingStones.add(talisman);
        return talisman;
    }

    public static ItemMod registerItem(ItemMod item) {
        items.add(item);
        return item;
    }

    public static List<ItemMod> getItems() {
        return Collections.unmodifiableList(items);
    }

    public static List<ItemTalisman> getTalismans() {
        return Collections.unmodifiableList(talismans);
    }

    public static List<ItemWishingStone> getWishingStones() {
        return Collections.unmodifiableList(wishingStones);
    }
}