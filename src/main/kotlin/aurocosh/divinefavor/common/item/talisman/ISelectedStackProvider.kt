package aurocosh.divinefavor.common.item.talisman

import net.minecraft.item.ItemStack

interface ISelectedStackProvider {
    fun getSelectedStack(stack: ItemStack): ItemStack
}