package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.item.ItemStack

fun ItemStack.isNotEmpty(): Boolean {
    return !this.isEmpty
}
