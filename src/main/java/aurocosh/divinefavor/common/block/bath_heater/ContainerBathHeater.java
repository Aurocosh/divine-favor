package aurocosh.divinefavor.common.block.bath_heater;

import aurocosh.divinefavor.common.item.base.GenericContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerBathHeater extends GenericContainer {
    private static final int PROGRESS_ID = 0;

    private TileBathHeater bathHeater;

    public ContainerBathHeater(EntityPlayer player, TileBathHeater bathHeater) {
        super(TileBathHeater.FUEL_SIZE + TileBathHeater.INGREDIENTS_SIZE);
        this.bathHeater = bathHeater;

        IItemHandler ingredientsHandler = this.bathHeater.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        this.addSlotToContainer(new SlotItemHandler(ingredientsHandler, 0, 40, 37));
        this.addSlotToContainer(new SlotItemHandler(ingredientsHandler, 1, 60, 17));
        this.addSlotToContainer(new SlotItemHandler(ingredientsHandler, 2, 100, 17));
        this.addSlotToContainer(new SlotItemHandler(ingredientsHandler, 3, 120, 37));

        IItemHandler fuelHandler = this.bathHeater.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        this.addSlotToContainer(new SlotItemHandler(fuelHandler, 0, 80, 58));

        generateInventorySlots(player.inventory, 8, 84);
        generateHotbarSlots(player.inventory, 8, 142);
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        return bathHeater.isUsableByPlayer(player);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if(bathHeater.getProgress() != bathHeater.getClientProgress()) {
            bathHeater.setClientProgress(bathHeater.getProgress());
            for (IContainerListener listener : listeners)
                listener.sendWindowProperty(this, PROGRESS_ID, bathHeater.getProgress());
        }
    }

    @Override
    public void updateProgressBar(int id, int data) {
        if (id == PROGRESS_ID)
            bathHeater.setClientProgress(data);
    }
}