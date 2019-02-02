package aurocosh.divinefavor.common.core.creative_tabs;

import aurocosh.divinefavor.common.item.base.ModItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Comparator;

public class ModItemStackComparator implements Comparator<ItemStack> {
    @Override
    public int compare(ItemStack first, ItemStack second) {
        Item firstItem = first.getItem();
        Item secondItem = second.getItem();

        boolean isFirstMod = firstItem instanceof ModItem;
        boolean isSecondMod = secondItem instanceof ModItem;

        if (isFirstMod && !isSecondMod)
            return -1;
        if (!isFirstMod && isSecondMod)
            return 1;
        if (isFirstMod && isSecondMod) {
            int firstOrder = ((ModItem) firstItem).getOrderIndex();
            int secondOrder = ((ModItem) secondItem).getOrderIndex();

            if (firstOrder == secondOrder)
                return firstItem.getRegistryName().toString().compareTo(secondItem.getRegistryName().toString());
            return firstOrder < secondOrder ? -1 : 1;
        }
        return firstItem.getRegistryName().toString().compareTo(secondItem.getRegistryName().toString());
    }
}
