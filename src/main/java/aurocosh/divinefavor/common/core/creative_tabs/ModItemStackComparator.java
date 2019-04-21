package aurocosh.divinefavor.common.core.creative_tabs;

import aurocosh.divinefavor.common.lib.interfaces.IOrdered;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Comparator;

public class ModItemStackComparator implements Comparator<ItemStack> {
    @Override
    public int compare(ItemStack first, ItemStack second) {
        Item firstItem = first.getItem();
        Item secondItem = second.getItem();

        boolean isFirstOrdered = firstItem instanceof IOrdered;
        boolean isSecondOrdered = secondItem instanceof IOrdered;

        if (isFirstOrdered && !isSecondOrdered)
            return -1;
        if (!isFirstOrdered && isSecondOrdered)
            return 1;
        if (isFirstOrdered && isSecondOrdered) {
            int firstOrder = ((IOrdered) firstItem).getOrderIndex();
            int secondOrder = ((IOrdered) secondItem).getOrderIndex();

            if (firstOrder == secondOrder)
                return firstItem.getRegistryName().compareTo(secondItem.getRegistryName());
            return firstOrder < secondOrder ? -1 : 1;
        }
        return firstItem.getRegistryName().compareTo(secondItem.getRegistryName());
    }
}
