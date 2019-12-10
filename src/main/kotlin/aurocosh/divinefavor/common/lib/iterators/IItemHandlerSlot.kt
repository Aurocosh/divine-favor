package aurocosh.divinefavor.common.lib.iterators

import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler

data class IItemHandlerSlot(val handler: IItemHandler, val index: Int, val stack: ItemStack) {
    fun extractItem(): ItemStack = handler.extractItem(index, stack.count, false)
}