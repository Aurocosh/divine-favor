package aurocosh.divinefavor.common.block.tile.container;

import aurocosh.divinefavor.common.block.tile.TileIronMedium;
import aurocosh.divinefavor.common.item.base.GenericContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerIronMedium extends GenericContainer {
    public TileIronMedium ironMedium;

    public ContainerIronMedium(EntityPlayer player, TileIronMedium ironMedium) {
        this.ironMedium = ironMedium;

        IItemHandler itemHandler = this.ironMedium.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        generateCustomSlotsGrid(itemHandler,8,18,3,9,0);
        generateInventorySlots(player.inventory,8,84);
        generateHotbarSlots(player.inventory,8,142);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return ironMedium.canInteractWith(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < TileIronMedium.SIZE) {
                if (!this.mergeItemStack(itemstack1, TileIronMedium.SIZE, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, TileIronMedium.SIZE, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
}