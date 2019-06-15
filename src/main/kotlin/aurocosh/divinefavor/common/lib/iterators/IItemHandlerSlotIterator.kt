package aurocosh.divinefavor.common.lib.iterators

import net.minecraftforge.items.IItemHandler

class IItemHandlerSlotIterator(private val handler: IItemHandler) : Iterator<IItemHandlerSlot> {
    private var currentIndex = 0
    private val size = handler.slots

    override fun hasNext(): Boolean {
        return currentIndex < size
    }

    override fun next(): IItemHandlerSlot {
        if (!this.hasNext()) {
            throw NoSuchElementException()
        }
        val stack = handler.getStackInSlot(currentIndex)
        return IItemHandlerSlot(handler, currentIndex++, stack)
    }
}