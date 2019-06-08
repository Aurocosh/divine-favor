package aurocosh.divinefavor.common.item.talisman_tools

import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.capability.ISpellBladeHandler
import net.minecraft.item.ItemStack
import net.minecraft.util.math.MathHelper
import net.minecraftforge.items.ItemStackHandler
import java.util.*

// The default implementation of the capability. Holds all the logic.
abstract class DefaultTalismanHandler(slotCount: Int) : ISpellBladeHandler {
    private val maxIndex = slotCount - 1
    private val stacksDisplayed = 3

    private var state: Int = 0
    private val inventory: ItemStackHandler
    override var selectedSlotIndex: Int = 0
        set(value) {
            field = MathHelper.clamp(value, 0, maxIndex)
            state++
        }

    init {
        state = 0
        selectedSlotIndex = 0
        inventory = object : ItemStackHandler(slotCount) {
            override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
                return isItemValid(stack)
            }

            override fun onContentsChanged(slot: Int) {
                super.onContentsChanged(slot)
                state++
            }
        }
    }

    abstract fun isItemValid(stack: ItemStack): Boolean

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

    override fun switchToNext() {
        selectedSlotIndex = getNonEmptyIndex(selectedSlotIndex, this::nextIndex)
    }

    override fun switchToPrevious() {
        selectedSlotIndex = getNonEmptyIndex(selectedSlotIndex, this::previousIndex)
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

    override fun getPreviousStacks(): List<ItemStack> {
        return getStacks(stacksDisplayed, this::previousIndex)
    }

    override fun getNextStacks(): List<ItemStack> {
        return getStacks(stacksDisplayed, this::nextIndex)
    }

    override fun getAllStacks(): List<ItemStack> {
        val stacks = ArrayList<ItemStack>()
        for (i in 0 until inventory.slots)
            stacks.add(inventory.getStackInSlot(i))
        return stacks
    }

    fun getStacks(count: Int, indexer: (Int) -> Int): List<ItemStack> {
        var counter = count
        val stacks = ArrayList<ItemStack>(counter)
        var index = selectedSlotIndex
        do {
            index = getNonEmptyIndex(index, indexer)
            if (index != selectedSlotIndex)
                stacks.add(inventory.getStackInSlot(index))
        } while (counter-- > 0 && index != selectedSlotIndex)
        return Collections.unmodifiableList(stacks)
    }

    private fun getNonEmptyIndex(index: Int, indexer: (Int) -> Int): Int {
        var newIndex = index
        do {
            newIndex = indexer.invoke(newIndex)
            val stack = inventory.getStackInSlot(newIndex)
            if (!stack.isEmpty)
                return newIndex
        } while (newIndex != index)
        return index
    }

    private fun nextIndex(index: Int): Int {
        var indexCounter = index
        indexCounter++
        if (indexCounter > maxIndex)
            indexCounter = 0
        return indexCounter
    }

    private fun previousIndex(index: Int): Int {
        var indexCounter = index
        indexCounter--
        if (indexCounter < 0)
            indexCounter = maxIndex
        return indexCounter
    }
}
