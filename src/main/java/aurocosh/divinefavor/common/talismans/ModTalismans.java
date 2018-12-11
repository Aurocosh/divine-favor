package aurocosh.divinefavor.common.talismans;

import aurocosh.divinefavor.common.constants.items.ConstTalismanNames;
import aurocosh.divinefavor.common.constants.items.ConstTalismanNames;
import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.registry.CommonRegistry;
import aurocosh.divinefavor.common.spell.common.ModSpells;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public final class ModTalismans {
    private static List<Talisman> talismanList = new ArrayList<>();
    private static final Map<ResourceLocation, Talisman> talismans = new HashMap<>();

    public static Talisman arrowThrowTalisman;
    public static Talisman bonemeal;
    public static Talisman empower_axe;
    public static Talisman fell_tree;
    public static Talisman ignition;
    public static Talisman lavawalking;
    public static Talisman small_fireball_throw;
    public static Talisman snowball_throw;
    public static Talisman stoneball_throw;
    public static Talisman waterwalking;
    public static Talisman wooden_punch;

    public static ItemStack getStack(Talisman talisman){
        return new ItemStack(ModItems.talisman,1, getMeta(talisman));
    }

    public static int getMeta(Talisman talisman){
        return talismanList.indexOf(talisman);
    }

    public static Talisman getByMeta(int meta){
        if(meta >= talismanList.size())
            return null;
        return talismanList.get(meta);
    }

    public static Collection<Talisman> getValues() {
        return talismans.values();
    }

    public static String[] getNames() {
        String[] names = new String[talismanList.size()];
        for (int i = 0; i < talismanList.size(); i++)
            names[i] = talismanList.get(i).name;
        return names;
    }

    public static void preInit() {
        arrowThrowTalisman = register(new TalismanBuilder(ConstTalismanNames.ARROW_THROW, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.arrow_throw)
                .castOnUse()
                .castOnRighClick()
                .create());
        bonemeal = register(new TalismanBuilder(ConstTalismanNames.BONEMEAL, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.bonemeal)
                .castOnUse()
                .create());
        empower_axe = register(new TalismanBuilder(ConstTalismanNames.EMPOWER_AXE, ModFavors.favor_of_timber, 1)
                .setSpell(ModSpells.empower_axe)
                .castOnUse()
                .castOnRighClick()
                .create());
        fell_tree = register(new TalismanBuilder(ConstTalismanNames.FELL_TREE, ModFavors.favor_of_timber, 1)
                .setSpell(ModSpells.fell_tree)
                .castOnUse()
                .create());
        ignition = register(new TalismanBuilder(ConstTalismanNames.IGNITION, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.ignition)
                .castOnUse()
                .create());
        lavawalking = register(new TalismanBuilder(ConstTalismanNames.LAVAWALKING, ModFavors.favor_of_allfire, 2)
                .setSpell(ModSpells.lavawalking)
                .castOnUse()
                .castOnRighClick()
                .create());
        snowball_throw = register(new TalismanBuilder(ConstTalismanNames.SNOWBALL_THROW, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.snowball_throw)
                .castOnUse()
                .castOnRighClick()
                .create());
        stoneball_throw = register(new TalismanBuilder(ConstTalismanNames.STONEBALL_THROW, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.stoneball_throw)
                .castOnUse()
                .castOnRighClick()
                .create());
        small_fireball_throw = register(new TalismanBuilder(ConstTalismanNames.SMALL_FIREBALL_THROW, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.small_fireball_throw)
                .castOnRighClick()
                .create());
        waterwalking = register(new TalismanBuilder(ConstTalismanNames.WATERWALKING, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.waterwalking)
                .castOnUse()
                .castOnRighClick()
                .create());
        wooden_punch = register(new TalismanBuilder(ConstTalismanNames.WOODEN_PUNCH, ModFavors.favor_of_timber, 2)
                .setSpell(ModSpells.wooden_punch)
                .castOnUse()
                .castOnRighClick()
                .create());
    }

    public static void init() {
//        OreDictionary.registerOre("dustPsi", new ItemStack(material, 1, 0));
//        OreDictionary.registerOre("ingotPsi", new ItemStack(material, 1, 1));
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

    private static Talisman register(Talisman talisman) {
        talismans.put(talisman.getRegistryName(), talisman);
        talismanList.add(talisman);
        CommonRegistry.register(talisman);
        return talisman;
    }
}