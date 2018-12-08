package aurocosh.divinefavor.common.item.ritual_pouch;

import aurocosh.divinefavor.common.item.base.GenericContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class RitualBagContainer extends GenericContainer {
    private int blocked;
    public RitualBagContainer(EntityPlayer player, IItemHandler itemHandler) {
//        IItemHandler itemHandler = this.ironMedium.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int x = 62;
        int y = 18;


        // Add our own slots
        int slotIndex = 0;
        for (int i = 0; i < 3; i++) {
            addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));
            x += 18;
        }

        x = 80;
        y = 36;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));

        x = 62;
        y = 54;
        for (int i = 0; i < 3; i++) {
            addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));
            x += 18;
        }



        generateInventorySlots(player.inventory,8,84);
        generateHotbarSlots(player.inventory,8,142);

        blocked = (inventorySlots.size() - 1) - (8 - player.inventory.currentItem);
    }

    @Override
    public ItemStack slotClick(int slot, int button, ClickType flag, EntityPlayer player)
    {
        if (slot == blocked)
            return ItemStack.EMPTY;
        return super.slotClick(slot, button, flag, player);
    }
}