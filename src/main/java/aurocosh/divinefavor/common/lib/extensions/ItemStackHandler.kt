package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.lib.iterators.IItemHandlerIterator
import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler

fun IItemHandler.asSequence(): Sequence<ItemStack> {
    return Sequence { IItemHandlerIterator(this) }
}
