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
    private static final int PROGRESS_BURNING_ID = 0;
    private static final int PROGRESS_EFFECT_ID = 1;

    private TileBathHeater bathHeater;

    public ContainerBathHeater(EntityPlayer player, TileBathHeater bathHeater) {
        super(TileBathHeater.FUEL_SIZE + TileBathHeater.INGREDIENTS_SIZE);
        this.bathHeater = bathHeater;

        IItemHandler blendHandler = this.bathHeater.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        this.addSlotToContainer(new SlotItemHandler(blendHandler, 0, 80, 23));

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
        if(bathHeater.getProgressBurning() != bathHeater.getClientProgressBurning()) {
            bathHeater.setClientProgressBurning(bathHeater.getProgressBurning());
            for (IContainerListener listener : listeners)
                listener.sendWindowProperty(this, PROGRESS_BURNING_ID, bathHeater.getProgressBurning());
        }
        if(bathHeater.getProgressEffect() != bathHeater.getClientProgressEffect()) {
            bathHeater.setClientProgressEffect(bathHeater.getProgressEffect());
            for (IContainerListener listener : listeners)
                listener.sendWindowProperty(this, PROGRESS_EFFECT_ID, bathHeater.getProgressEffect());
        }
    }

    @Override
    public void updateProgressBar(int id, int data) {
        if (id == PROGRESS_BURNING_ID)
            bathHeater.setClientProgressBurning(data);
        else if (id == PROGRESS_EFFECT_ID)
            bathHeater.setClientProgressEffect(data);
    }
}