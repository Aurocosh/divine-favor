package aurocosh.divinefavor.common.item.base

import aurocosh.divinefavor.common.lib.int_interval.IntInterval
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler
import java.util.*

open class GenericContainer(private val inventorySize: Int) : Container() {
    private val slotsToTransfer: MutableList<IntInterval>

    init {
        slotsToTransfer = ArrayList()
        addTransferSlotRange(0, inventorySize - 1)
    }

    protected fun clearTransferRanges() {
        slotsToTransfer.clear()
    }

    protected fun addTransferSlotRange(start: Int, stop: Int) {
        slotsToTransfer.add(IntInterval(start, stop))
    }

    protected fun optimizeIntervals() {
        val optimal = IntInterval.optimize(slotsToTransfer)
        slotsToTransfer.clear()
        slotsToTransfer.addAll(optimal)
    }

    private fun canTransferTo(slotIndex: Int): Boolean {
        for (interval in slotsToTransfer)
            if (interval.isInsideInclusive(slotIndex))
                return true
        return false
    }

    override fun canInteractWith(player: EntityPlayer): Boolean {
        return true
    }

    protected fun generateInventorySlots(playerInventory: InventoryPlayer, xStart: Int, yStart: Int) {
        for (row in 0..2) {
            for (col in 0..8) {
                val x = xStart + col * 18
                val y = yStart + row * 18
                addSlotToContainer(Slot(playerInventory, col + row * 9 + 9, x, y))
            }
        }
    }

    protected fun generateHotbarSlots(playerInventory: InventoryPlayer, xStart: Int, yStart: Int) {
        for (i in 0..8) {
            val x = xStart + i * 18
            addSlotToContainer(Slot(playerInventory, i, x, yStart))
        }
    }

    protected fun generateCustomSlotsGrid(itemHandler: IItemHandler, xStart: Int, yStart: Int, rows: Int, columns: Int, nextSlotIndex: Int): Int {
        var nextSlotIndex = nextSlotIndex
        for (row in 0 until rows) {
            for (col in 0 until columns) {
                val x = xStart + col * 18
                val y = yStart + row * 18
                addSlotToContainer(SlotItemHandler(itemHandler, nextSlotIndex++, x, y))
            }
        }
        return nextSlotIndex
    }

    override fun transferStackInSlot(playerIn: EntityPlayer?, index: Int): ItemStack {
        val slot = inventorySlots[index]
        if (slot == null || !slot.hasStack)
            return ItemStack.EMPTY

        val itemStack = slot.stack
        val itemStackCopy = itemStack.copy()

        if (index < inventorySize && canTransferTo(index) && mergeItemStack(itemStack, inventorySize, inventorySlots.size, true))
            return itemStackCopy
        else if (mergeItemStack(itemStack, 0, inventorySize, false))
            return itemStackCopy

        if (itemStack.isEmpty)
            slot.putStack(ItemStack.EMPTY)
        else
            slot.onSlotChanged()
        return ItemStack.EMPTY
    }
}
