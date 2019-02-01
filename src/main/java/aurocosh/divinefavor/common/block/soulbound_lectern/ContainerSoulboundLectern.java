package aurocosh.divinefavor.common.block.soulbound_lectern;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.contracts.ContractsData;
import aurocosh.divinefavor.common.item.ItemBloodCrystal;
import aurocosh.divinefavor.common.item.base.GenericContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.UUID;

public class ContainerSoulboundLectern extends GenericContainer {
    private static final int CRYSTAL_SLOT = 0;

    private final boolean isBlocked;
    private final TileSoulboundLectern soulboundLectern;
    public ContainerSoulboundLectern(EntityPlayer player, TileSoulboundLectern soulboundLectern) {
        this.soulboundLectern = soulboundLectern;

        IItemHandler crystalHandler = this.soulboundLectern.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        this.addSlotToContainer(new SlotItemHandler(crystalHandler, 0, 80, 36));

        assert crystalHandler != null;
        ItemStack stack = crystalHandler.getStackInSlot(0);
        UUID playerUUID = player.getGameProfile().getId();
        UUID stackUUID = ItemBloodCrystal.getPlayerId(stack);
        isBlocked = playerUUID.equals(stackUUID);
        if (isBlocked) {
            ContractsData contractsData = PlayerData.get(player).getContractsData();
            ItemStackHandler stackHandler = contractsData.getStackHandler();

            int nextSlotIndex = 0;
            generateCustomSlotsGrid(stackHandler, 8, 18, 1, 4, nextSlotIndex);
            generateCustomSlotsGrid(stackHandler, 98, 18, 1, 4, nextSlotIndex);
            generateCustomSlotsGrid(stackHandler, 26, 36, 1, 3, nextSlotIndex);
            generateCustomSlotsGrid(stackHandler, 98, 36, 1, 3, nextSlotIndex);
            generateCustomSlotsGrid(stackHandler, 8, 54, 1, 4, nextSlotIndex);
            generateCustomSlotsGrid(stackHandler, 98, 54, 1, 4, nextSlotIndex);
        }

        generateInventorySlots(player.inventory, 8, 84);
        generateHotbarSlots(player.inventory, 8, 142);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return soulboundLectern.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < TileSoulboundLectern.SIZE) {
                if (!this.mergeItemStack(itemstack1, TileSoulboundLectern.SIZE, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, TileSoulboundLectern.SIZE, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }
            else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }


    @Override
    public ItemStack slotClick(int slot, int button, ClickType flag, EntityPlayer player)
    {
        if (isBlocked && slot == CRYSTAL_SLOT)
            return ItemStack.EMPTY;
        return super.slotClick(slot, button, flag, player);
    }
}