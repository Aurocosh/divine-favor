package aurocosh.divinefavor.common.core.creative_tabs

import aurocosh.divinefavor.common.lib.interfaces.IOrdered
import net.minecraft.item.ItemStack
import java.util.*

class ModItemStackComparator : Comparator<ItemStack> {
    override fun compare(first: ItemStack, second: ItemStack): Int {
        val firstItem = first.item
        val secondItem = second.item

        val isFirstOrdered = firstItem is IOrdered
        val isSecondOrdered = secondItem is IOrdered

        if (isFirstOrdered && !isSecondOrdered)
            return -1
        if (!isFirstOrdered && isSecondOrdered)
            return 1
        if (isFirstOrdered && isSecondOrdered) {
            val firstOrder = (firstItem as IOrdered).orderIndex
            val secondOrder = (secondItem as IOrdered).orderIndex

            if (firstOrder == secondOrder)
                return firstItem.registryName!!.compareTo(secondItem.registryName!!)
            return if (firstOrder < secondOrder) -1 else 1
        }
        return firstItem.registryName!!.compareTo(secondItem.registryName!!)
    }
}
