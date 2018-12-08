package aurocosh.divinefavor.common.block.medium;

import aurocosh.divinefavor.common.item.base.GenericContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMedium extends GenericContainer {
    public TileMedium ironMedium;

    public ContainerMedium(EntityPlayer player, TileMedium ironMedium) {
        this.ironMedium = ironMedium;

        IItemHandler figurineHandler = this.ironMedium.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        this.addSlotToContainer(new SlotItemHandler(figurineHandler, 0, 80, 36));

        IItemHandler itemHandler = this.ironMedium.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int slotIndex = generateCustomSlotsGrid(itemHandler,8,18,3,3,0);
        generateCustomSlotsGrid(itemHandler,116,18,3,3, slotIndex);

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

            if (index < TileMedium.SIZE) {
                if (!this.mergeItemStack(itemstack1, TileMedium.SIZE, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, TileMedium.SIZE, false)) {
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