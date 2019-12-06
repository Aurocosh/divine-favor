package aurocosh.divinefavor.common.item.gem_pouch.capability

import aurocosh.divinefavor.common.item.gem_pouch.ItemGemPouch
import aurocosh.divinefavor.common.item.gems.ItemWarpMarker
import aurocosh.divinefavor.common.item.talisman_tools.DefaultStackContainerHandler
import net.minecraft.item.ItemStack

// The default implementation of the capability. Holds all the logic.
class DefaultGemPouchHandler : DefaultStackContainerHandler(ItemGemPouch.SLOT_COUNT), IGemPouchHandler {
    override fun isItemValid(stack: ItemStack): Boolean {
        return stack.item is ItemWarpMarker
    }
}
