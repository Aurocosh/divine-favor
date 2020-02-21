package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack

fun InventoryCrafting.asSequence(): Sequence<ItemStack> {
    return indices.S.map(this::getStackInSlot)
}

val InventoryCrafting.indices: IntRange get() = (0..(this.sizeInventory-1))
