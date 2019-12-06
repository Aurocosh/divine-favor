package aurocosh.divinefavor.common.item.talisman_tools

import net.minecraft.item.ItemStack
import net.minecraftforge.items.ItemStackHandler

interface IStackContainer {
    var selectedSlotIndex: Int

    fun getSlotCount(): Int

    fun getSelectedStack(): ItemStack
    fun getPreviousStacks(): List<ItemStack>
    fun getNextStacks(): List<ItemStack>
    fun getAllStacks(): List<ItemStack>

    fun getStackHandler(): ItemStackHandler

    fun getState(): Int

    fun switchToNext()
    fun switchToPrevious()
    fun getStackInSlot(slot: Int): ItemStack
    fun getSlotIndexes(predicate: (ItemStack) -> Boolean): List<Int>
}
