package aurocosh.divinefavor.client.core.handler.common

import aurocosh.divinefavor.client.core.handler.base.IDisplayStackExtractor
import net.minecraft.item.ItemStack

object StandardDisplayStackExtractor : IDisplayStackExtractor {
    override fun getDisplayStack(stack: ItemStack): ItemStack {
        return stack
    }
}