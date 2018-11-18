package aurocosh.divinefavor.common.item.base;

import aurocosh.divinefavor.common.item.ItemTalisman;
import vazkii.arl.item.ItemMod;

public final class ModItems {

    public static ItemMod talisman;

    public static void preInit() {
        talisman = new ItemTalisman();
    }

    public static void init() {
        // Psi oredict mappings
//        OreDictionary.registerOre("dustPsi", new ItemStack(material, 1, 0));
//        OreDictionary.registerOre("ingotPsi", new ItemStack(material, 1, 1));
    }
}