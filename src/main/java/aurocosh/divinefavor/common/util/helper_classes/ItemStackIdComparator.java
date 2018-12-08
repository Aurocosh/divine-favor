package aurocosh.divinefavor.common.util.helper_classes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Comparator;

public class ItemStackIdComparator implements Comparator<ItemStack> {
    @Override
    public int compare(ItemStack o1, ItemStack o2) {
        int id1 = Item.REGISTRY.getIDForObject(o1.getItem());
        int id2 = Item.REGISTRY.getIDForObject(o2.getItem());

        return id1 - id2;
    }
}
