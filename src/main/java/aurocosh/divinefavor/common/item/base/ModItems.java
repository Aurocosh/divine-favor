package aurocosh.divinefavor.common.item.base;

import aurocosh.divinefavor.common.item.ItemArrowThrowTalisman;
import aurocosh.divinefavor.common.item.ItemBonemealTalisman;
import aurocosh.divinefavor.common.item.ItemIgnitionTalisman;
import vazkii.arl.item.ItemMod;

public final class ModItems {

    public static ItemMod arrow_throw_talisman;
    public static ItemMod bonemeal_talisman;
    public static ItemMod ignition_talisman;

    public static void preInit() {
        arrow_throw_talisman = new ItemArrowThrowTalisman();
        bonemeal_talisman = new ItemBonemealTalisman();
        ignition_talisman = new ItemIgnitionTalisman();
    }

    public static void init() {
        // Psi oredict mappings
//        OreDictionary.registerOre("dustPsi", new ItemStack(material, 1, 0));
//        OreDictionary.registerOre("ingotPsi", new ItemStack(material, 1, 1));
    }
}