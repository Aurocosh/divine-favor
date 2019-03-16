package aurocosh.divinefavor.common.block.soulbound_lectern;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorData;
import aurocosh.divinefavor.common.item.base.GenericContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ContainerSoulboundLecternBound extends GenericContainer {
    private final TileSoulboundLectern soulboundLectern;
    private final FavorData favorData;

    public ContainerSoulboundLecternBound(EntityPlayer player, TileSoulboundLectern soulboundLectern) {
        super(FavorData.CONTRACT_SLOT_COUNT);
        this.soulboundLectern = soulboundLectern;

        favorData = PlayerData.get(player).getFavorData();
        ItemStackHandler stackHandler = favorData.getContractHandler(soulboundLectern.getFavorId());

        int nextSlotIndex = 0;
        nextSlotIndex = generateCustomSlotsGrid(stackHandler, 8, 18, 1, 4, nextSlotIndex);
        nextSlotIndex = generateCustomSlotsGrid(stackHandler, 98, 18, 1, 4, nextSlotIndex);
        nextSlotIndex = generateCustomSlotsGrid(stackHandler, 26, 36, 1, 3, nextSlotIndex);
        nextSlotIndex = generateCustomSlotsGrid(stackHandler, 98, 36, 1, 3, nextSlotIndex);
        nextSlotIndex = generateCustomSlotsGrid(stackHandler, 8, 54, 1, 4, nextSlotIndex);
        nextSlotIndex = generateCustomSlotsGrid(stackHandler, 98, 54, 1, 4, nextSlotIndex);

        generateInventorySlots(player.inventory, 8, 84);
        generateHotbarSlots(player.inventory, 8, 142);
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        return soulboundLectern.isUsableByPlayer(player);
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        favorData.refreshContracts();
    }
}