package aurocosh.divinefavor.common.item.talisman;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerTalisman extends Container{
    private final ItemStack parentTalisman;

    public ContainerTalisman(EntityPlayer player, ItemStack talisman) {
        this.parentTalisman = talisman;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}