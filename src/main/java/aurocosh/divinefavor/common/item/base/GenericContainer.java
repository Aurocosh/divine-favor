package aurocosh.divinefavor.common.item.base;

import aurocosh.divinefavor.common.block.tile.TileIronMedium;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

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
