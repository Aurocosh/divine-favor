package aurocosh.divinefavor.common.item.memory_pouch.capability

import aurocosh.divinefavor.common.item.memory_drop.ItemMemoryDrop
import aurocosh.divinefavor.common.item.memory_pouch.ItemMemoryPouch
import net.minecraft.item.ItemStack
import net.minecraft.util.math.MathHelper
import net.minecraftforge.items.ItemStackHandler
import java.util.*

// The default implementation of the capability. Holds all the logic.
class DefaultMemoryPouchHandler : IMemoryPouch {
    private val slotCount = ItemMemoryPouch.SLOT_COUNT
    private val maxIndex = slotCount - 1

    private var state: Int = 0
    override var selectedSlotIndex: Int = 0
        set(value) {
            field = MathHelper.clamp(value, 0, maxIndex)
            state++
        }

    private val inventory: ItemStackHandler = object : ItemStackHandler(slotCount) {
        override fun isItemValid(slot: Int, stack: ItemStack) = stack.item is ItemMemoryDrop
        override fun onContentsChanged(slot: Int) {
            super.onContentsChanged(slot)
            state++
        }
    }

    override fun getSelectedStack(): ItemStack {
        return inventory.getStackInSlot(selectedSlotIndex)
    }

    override fun getStackInSlot(slot: Int): ItemStack {
        return inventory.getStackInSlot(slot)
    }

    override fun getSlotIndexes(predicate: (ItemStack) -> Boolean): List<Int> {
        val indexes = ArrayList<Int>()
        for (i in 0 until inventory.slots) {
            val stack = inventory.getStackInSlot(i)
            if (predicate.invoke(stack))
                indexes.add(i)
        }
        return indexes
    }

    override fun getSlotCount(): Int {
        return inventory.slots
    }

    override fun getStackHandler(): ItemStackHandler {
        return inventory
    }

    override fun getState(): Int {
        return state
    }

    override fun getAllStacks(): List<ItemStack> {
        val stacks = ArrayList<ItemStack>()
        for (i in 0 until inventory.slots)
            stacks.add(inventory.getStackInSlot(i))
        return stacks
    }
}
