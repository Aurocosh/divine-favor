package aurocosh.divinefavor.common.block.medium;

import aurocosh.divinefavor.common.item.base.GenericContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerMedium extends GenericContainer {
    public TileMedium ironMedium;

    public ContainerMedium(EntityPlayer player, TileMedium ironMedium) {
        super(TileMedium.SIZE);
        this.ironMedium = ironMedium;

        IItemHandler stoneHandler = this.ironMedium.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        this.addSlotToContainer(new SlotItemHandler(stoneHandler, 0, 80, 36));

        IItemHandler leftHandler = this.ironMedium.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.WEST);
        generateCustomSlotsGrid(leftHandler, 8, 18, 3, 3, 0);

        IItemHandler rightHandler = this.ironMedium.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.EAST);
        generateCustomSlotsGrid(rightHandler, 116, 18, 3, 3, 0);

        generateInventorySlots(player.inventory, 8, 84);
        generateHotbarSlots(player.inventory, 8, 142);
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        return ironMedium.isUsableByPlayer(player);
    }
}