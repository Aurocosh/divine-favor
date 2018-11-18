package aurocosh.divinefavor.common.item.base;

import aurocosh.divinefavor.common.item.*;
import vazkii.arl.item.ItemMod;

public final class ModItems {

    public static ItemMod arrow_throw_talisman;
    public static ItemMod bonemeal_talisman;
    public static ItemMod ignition_talisman;
    public static ItemMod snowball_throw_talisman;
    public static ItemMod stoneball_throw_talisman;
    public static ItemMod stoneball;

    public static void preInit() {
        arrow_throw_talisman = new ItemArrowThrowTalisman();
        bonemeal_talisman = new ItemBonemealTalisman();
        ignition_talisman = new ItemIgnitionTalisman();
        snowball_throw_talisman = new ItemSnowballThrowTalisman();
        stoneball_throw_talisman = new ItemStoneballThrowTalisman();
        stoneball = new ItemStoneball();
    }

    public static void init() {
        // Psi oredict mappings
//        OreDictionary.registerOre("dustPsi", new ItemStack(material, 1, 0));
//        OreDictionary.registerOre("ingotPsi", new ItemStack(material, 1, 1));
    }
}