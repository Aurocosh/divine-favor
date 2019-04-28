package aurocosh.divinefavor.common.util

import net.minecraft.item.ItemStack

data class SlotData(val slotIndex: Int, val stack: ItemStack) {
    val isValid: Boolean
        get() = slotIndex > 0 || slotIndex < InventoryIndexes.Offhand.value
}
