package aurocosh.divinefavor.common.block.soulbound_lectern;

import aurocosh.divinefavor.common.item.base.GenericContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerSoulboundLecternUnbound extends GenericContainer {
    private final TileSoulboundLectern soulboundLectern;
    public ContainerSoulboundLecternUnbound(EntityPlayer player, TileSoulboundLectern soulboundLectern) {
        super(1);
        this.soulboundLectern = soulboundLectern;

        IItemHandler crystalHandler = this.soulboundLectern.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        this.addSlotToContainer(new SlotItemHandler(crystalHandler, 0, 80, 36));

        generateInventorySlots(player.inventory, 8, 84);
        generateHotbarSlots(player.inventory, 8, 142);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return soulboundLectern.isUsableByPlayer(playerIn);
    }
}