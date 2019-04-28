package aurocosh.divinefavor.common.item.talisman_container

import net.minecraft.item.ItemStack
import net.minecraftforge.items.ItemStackHandler
import java.util.function.Predicate

interface ITalismanContainer {
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
    fun getSlotIndexes(predicate: Predicate<ItemStack>): List<Int>
}
