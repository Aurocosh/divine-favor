package aurocosh.divinefavor.client.core.handler.base

import net.minecraft.item.ItemStack

interface IDisplayStackExtractor {
    fun getDisplayStack(stack: ItemStack): ItemStack
}