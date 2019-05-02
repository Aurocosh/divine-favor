package aurocosh.divinefavor.common.lib

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import java.util.*

class ItemStackIdComparator : Comparator<ItemStack> {
    override fun compare(o1: ItemStack, o2: ItemStack): Int {
        val id1 = Item.REGISTRY.getIDForObject(o1.item)
        val id2 = Item.REGISTRY.getIDForObject(o2.item)

        val idDifference = id1 - id2
        return if (idDifference != 0) idDifference else o1.itemDamage - o2.itemDamage
    }
}
