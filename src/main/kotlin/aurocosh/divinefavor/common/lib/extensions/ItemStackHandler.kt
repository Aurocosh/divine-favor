package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.lib.iterators.IItemHandlerIterator
import aurocosh.divinefavor.common.lib.iterators.IItemHandlerSlot
import aurocosh.divinefavor.common.lib.iterators.IItemHandlerSlotIterator
import aurocosh.divinefavor.common.util.UtilItemStack
import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler

fun IItemHandler.asSequence(): Sequence<ItemStack> {
    return Sequence { IItemHandlerIterator(this) }
}

fun IItemHandler.asSlotSequence(): Sequence<IItemHandlerSlot> {
    return Sequence { IItemHandlerSlotIterator(this) }
}

fun IItemHandler.extractItem(slot: IItemHandlerSlot, simulate: Boolean = false): ItemStack {
    return this.extractItem(slot.index, slot.stack.count, simulate)
}

fun IItemHandler.insertStack(stack: ItemStack): ItemStack {
    var leftoverStack = stack.copy()
    while (leftoverStack.isNotEmpty()) {
        val placeForStack = findPlaceForStack(leftoverStack) ?: return leftoverStack
        leftoverStack = this.insertItem(placeForStack.index, leftoverStack, false)
    }
    return ItemStack.EMPTY
}

fun IItemHandler.findPlaceForStack(stack: ItemStack): IItemHandlerSlot? {
    return this.asSlotSequence().firstOrNull { UtilItemStack.canMergeStacks(it.stack, stack) || it.stack.isEmpty }
}

fun IItemHandler.moveItemsFromSlots(slots: List<IItemHandlerSlot>) {
    for (handlerSlot in slots) {
        val leftoverStack = this.insertStack(handlerSlot.stack)
        handlerSlot.handler.extractItem(handlerSlot.index, handlerSlot.stack.count - leftoverStack.count, false)
    }
}