package aurocosh.divinefavor.common.item.memory_pouch.capability

import net.minecraft.item.ItemStack
import net.minecraftforge.items.ItemStackHandler

interface IMemoryPouch {
    var selectedSlotIndex: Int

    fun getSlotCount(): Int

    fun getSelectedStack(): ItemStack
    fun getAllStacks(): List<ItemStack>

    fun getStackHandler(): ItemStackHandler

    fun getState(): Int

    fun getStackInSlot(slot: Int): ItemStack
    fun getSlotIndexes(predicate: (ItemStack) -> Boolean): List<Int>
}
