package aurocosh.divinefavor.common.item.grimoire;

import aurocosh.divinefavor.common.item.base.GenericContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class GrimoireContainer extends GenericContainer {
    private int blocked;

    public GrimoireContainer(EntityPlayer player, IItemHandler itemHandler) {
        int x = 8;
        int y = 18;
        int slotIndex = 0;
        // Add our own slots
        generateCustomSlotsGrid(itemHandler, x, y, 3, 9, slotIndex);

        generateInventorySlots(player.inventory, 8, 84);
        generateHotbarSlots(player.inventory, 8, 142);

        blocked = (inventorySlots.size() - 1) - (8 - player.inventory.currentItem);
    }

    @Override
    public ItemStack slotClick(int slot, int button, ClickType flag, EntityPlayer player) {
        if (slot == blocked)
            return ItemStack.EMPTY;
        return super.slotClick(slot, button, flag, player);
    }
}