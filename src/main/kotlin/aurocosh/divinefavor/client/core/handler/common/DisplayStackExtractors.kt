package aurocosh.divinefavor.client.core.handler.common

import aurocosh.divinefavor.client.core.handler.base.IDisplayStackExtractor
import aurocosh.divinefavor.common.item.gems.ItemWarpMarker
import net.minecraft.item.ItemStack

object DisplayStackExtractors {
    fun getDisplayStack(stack: ItemStack): ItemStack {
        return getDisplayStackExtractor(stack).getDisplayStack(stack)
    }

    private fun getDisplayStackExtractor(stack: ItemStack): IDisplayStackExtractor {
        return when (stack.item) {
            is ItemWarpMarker -> WarpMarkerDisplayStackExtractor
            else -> StandardDisplayStackExtractor
        }
    }
}