package aurocosh.divinefavor.common.item.base;

import aurocosh.divinefavor.common.lib.int_interval.IntInterval;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.ArrayList;
import java.util.List;

public class GenericContainer extends Container {
    private final int inventorySize;
    private final List<IntInterval> slotsToTransfer;

    public GenericContainer(int inventorySize) {
        this.inventorySize = inventorySize;
        slotsToTransfer = new ArrayList<>();
        addTransferSlotRange(0, inventorySize - 1);
    }

    protected void clearTransferRanges() {
        slotsToTransfer.clear();
    }

    protected void addTransferSlotRange(int start, int stop) {
        slotsToTransfer.add(new IntInterval(start, stop));
    }

    protected void optimizeIntervals() {
        List<IntInterval> optimal = IntInterval.optimize(slotsToTransfer);
        slotsToTransfer.clear();
        slotsToTransfer.addAll(optimal);
    }

    private boolean canTransferTo(int slotIndex) {
        for (IntInterval interval : slotsToTransfer)
            if (interval.isInsideInclusive(slotIndex))
                return true;
        return false;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    protected void generateInventorySlots(InventoryPlayer playerInventory, int xStart, int yStart) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = xStart + col * 18;
                int y = yStart + row * 18;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }
    }

    protected void generateHotbarSlots(InventoryPlayer playerInventory, int xStart, int yStart) {
        for (int row = 0; row < 9; ++row) {
            int x = xStart + row * 18;
            this.addSlotToContainer(new Slot(playerInventory, row, x, yStart));
        }
    }

    protected int generateCustomSlotsGrid(IItemHandler itemHandler, int xStart, int yStart, int rows, int columns, int nextSlotIndex) {
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < columns; ++col) {
                int x = xStart + col * 18;
                int y = yStart + row * 18;
                this.addSlotToContainer(new SlotItemHandler(itemHandler, nextSlotIndex++, x, y));
            }
        }
        return nextSlotIndex;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        Slot slot = inventorySlots.get(index);
        if (slot == null || !slot.getHasStack())
            return ItemStack.EMPTY;

        ItemStack itemStack = slot.getStack();
        ItemStack itemStackCopy = itemStack.copy();

        if (index < inventorySize && canTransferTo(index) && mergeItemStack(itemStack, inventorySize, inventorySlots.size(), true))
            return itemStackCopy;
        else if (mergeItemStack(itemStack, 0, inventorySize, false) )
            return itemStackCopy;

        if (itemStack.isEmpty())
            slot.putStack(ItemStack.EMPTY);
        else
            slot.onSlotChanged();
        return ItemStack.EMPTY;
    }
}
