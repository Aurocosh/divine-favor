package aurocosh.divinefavor.common.lib.iterators

import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler

class IItemHandlerIterator(private val handler: IItemHandler) : Iterator<ItemStack> {
    private var currentIndex = 0
    private val size = handler.slots

    override fun hasNext(): Boolean {
        return currentIndex < size
    }

    override fun next(): ItemStack {
        if (!this.hasNext()) {
            throw NoSuchElementException()
        }
        return handler.getStackInSlot(currentIndex++)
    }
}