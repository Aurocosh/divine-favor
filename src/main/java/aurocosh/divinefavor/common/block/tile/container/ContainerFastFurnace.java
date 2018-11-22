package aurocosh.divinefavor.common.block.tile.container;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.tile.TileFastFurnace;
import aurocosh.divinefavor.common.block.tile.TileIronMedium;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFastFurnace extends Container {
    private static final int PROGRESS_ID = 0;

    public TileFastFurnace fastFurnace;

    public ContainerFastFurnace(EntityPlayer player, TileFastFurnace fastFurnace) {
        this.fastFurnace = fastFurnace;

        addPlayerSlots(player.inventory);
        addOwnSlots();
    }

    private void addPlayerSlots(IInventory playerInventory) {
        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 10 + col * 18;
                int y = row * 18 + 70;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 10, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = 10 + row * 18;
            int y = 58 + 70;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    private void addOwnSlots() {
        IItemHandler itemHandler = this.fastFurnace.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int slotCount = itemHandler.getSlots();
        if(slotCount % 2 != 0)
            DivineFavor.logger.error("Furnace slot count is incorrect");
        slotCount = slotCount - slotCount % 2;
        int subount = slotCount / 2;

        int x = 10;
        int y = 6;

        // Add our own slots
        int slotIndex = 0;
        for (int i = 0; i < subount; i++) {
            addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x, y));
            slotIndex++;
            x += 18;
        }

        x = 118;

        for (int i = 0; i < subount; i++) {
            addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x, y));
            slotIndex++;
            x += 18;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return fastFurnace.canInteractWith(playerIn);
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

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (IContainerListener listener : listeners) {
            listener.sendWindowProperty(this, PROGRESS_ID, fastFurnace.getProgress());
        }
    }
    @Override
    public void updateProgressBar(int id, int data) {
        if (id == PROGRESS_ID) {
            fastFurnace.setProgress(data);
        }
    }
}