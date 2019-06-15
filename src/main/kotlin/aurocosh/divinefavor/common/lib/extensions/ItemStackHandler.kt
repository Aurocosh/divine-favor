package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.lib.iterators.IItemHandlerIterator
import aurocosh.divinefavor.common.lib.iterators.IItemHandlerSlot
import aurocosh.divinefavor.common.lib.iterators.IItemHandlerSlotIterator
import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler

fun IItemHandler.asSequence(): Sequence<ItemStack> {
    return Sequence { IItemHandlerIterator(this) }
}

fun IItemHandler.asSlotSequence(): Sequence<IItemHandlerSlot> {
    return Sequence { IItemHandlerSlotIterator(this) }
}