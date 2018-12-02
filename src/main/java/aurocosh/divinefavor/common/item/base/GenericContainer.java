package aurocosh.divinefavor.common.item.base;

import aurocosh.divinefavor.common.block.tile.TileIronMedium;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class GenericContainer extends Container {
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
    return true;
  }

    protected void generateInventorySlots(InventoryPlayer playerInventory, int xStart, int yStart){
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = xStart + col * 18;
                int y = yStart + row * 18;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }
    }

    protected void generateHotbarSlots(InventoryPlayer playerInventory, int xStart, int yStart){
        for (int row = 0; row < 9; ++row) {
            int x = xStart + row * 18;
            this.addSlotToContainer(new Slot(playerInventory, row, x, yStart));
        }
    }

    protected int generateCustomSlotsGrid(IItemHandler itemHandler, int xStart, int yStart, int rows, int columns, int nextSlotIndex){
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
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack otherItemstack = slot.getStack();
            itemstack = otherItemstack.copy();

            if (index < TileIronMedium.SIZE) {
                if (!this.mergeItemStack(otherItemstack, TileIronMedium.SIZE, this.inventorySlots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!this.mergeItemStack(otherItemstack, 0, TileIronMedium.SIZE, false))
                return ItemStack.EMPTY;

            if (otherItemstack.isEmpty())
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
        }

        return itemstack;
    }
}
