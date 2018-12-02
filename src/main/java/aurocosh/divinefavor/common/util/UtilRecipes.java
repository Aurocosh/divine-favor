package aurocosh.divinefavor.common.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UtilRecipes {
    public static int packWithoutMeta(ItemStack stack)
    {
        Item item = stack.getItem();
        int i = item.getHasSubtypes() ? stack.getMetadata() : 0;
        return Item.REGISTRY.getIDForObject(item) << 16 | i & 65535;
    }

}
