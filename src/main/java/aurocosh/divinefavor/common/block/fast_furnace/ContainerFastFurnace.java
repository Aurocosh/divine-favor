package aurocosh.divinefavor.common.block.fast_furnace;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.medium.TileMedium;
import aurocosh.divinefavor.common.network.common.NetworkHandler;
import aurocosh.divinefavor.common.network.message.client.MessageSyncPower;
import aurocosh.divinefavor.common.tool.IEnergyContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFastFurnace extends Container implements IEnergyContainer {
    private static final int PROGRESS_ID = 0;

    private TileFastFurnace fastFurnace;

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
        int y = 19;

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
            ItemStack otherItemstack = slot.getStack();
            itemstack = otherItemstack.copy();

            if (index < TileMedium.SIZE) {
                if (!this.mergeItemStack(otherItemstack, TileMedium.SIZE, this.inventorySlots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!this.mergeItemStack(otherItemstack, 0, TileMedium.SIZE, false))
                return ItemStack.EMPTY;

            if (otherItemstack.isEmpty())
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
        }

        return itemstack;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if(fastFurnace.getProgress() != fastFurnace.getClientProgress()) {
            fastFurnace.setClientProgress(fastFurnace.getProgress());
            for (IContainerListener listener : listeners)
                listener.sendWindowProperty(this, PROGRESS_ID, fastFurnace.getProgress());
        }
        if (fastFurnace.getEnergy() != fastFurnace.getClientEnergy()) {
            fastFurnace.setClientEnergy(fastFurnace.getEnergy());
            for (IContainerListener listener : listeners) {
                if (listener instanceof EntityPlayerMP) {
                    EntityPlayerMP player = (EntityPlayerMP) listener;
                    MessageSyncPower message = new MessageSyncPower(fastFurnace.getEnergy());
                    NetworkHandler.INSTANCE.sendTo(message, player);
                }
            }
        }
    }
    @Override
    public void updateProgressBar(int id, int data) {
        if (id == PROGRESS_ID) {
            fastFurnace.setClientProgress(data);
        }
    }

    @Override
    public void syncPower(int energy) {
        fastFurnace.setClientEnergy(energy);
    }
}