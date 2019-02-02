package aurocosh.divinefavor.common.item.contract_binder;

import aurocosh.divinefavor.common.item.base.GenericContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class ContractBinderContainer extends GenericContainer {
    private int blocked;

    public ContractBinderContainer(EntityPlayer player, IItemHandler itemHandler) {
        super(ItemContractBinder.SIZE);
        generateCustomSlotsGrid(itemHandler, 26, 18, 1, 7, 0);
        generateInventorySlots(player.inventory, 8, 48);
        generateHotbarSlots(player.inventory, 8, 106);

        blocked = (inventorySlots.size() - 1) - (8 - player.inventory.currentItem);
    }

    @Override
    public ItemStack slotClick(int slot, int button, ClickType flag, EntityPlayer player) {
        if (slot == blocked)
            return ItemStack.EMPTY;
        return super.slotClick(slot, button, flag, player);
    }
}