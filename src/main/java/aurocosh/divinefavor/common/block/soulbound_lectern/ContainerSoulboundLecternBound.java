package aurocosh.divinefavor.common.block.soulbound_lectern;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.contracts.ContractsData;
import aurocosh.divinefavor.common.item.base.GenericContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.ItemStackHandler;

public class ContainerSoulboundLecternBound extends GenericContainer {
    private final TileSoulboundLectern soulboundLectern;

    public ContainerSoulboundLecternBound(EntityPlayer player, TileSoulboundLectern soulboundLectern) {
        super(ContractsData.SIZE);
        this.soulboundLectern = soulboundLectern;

        ContractsData contractsData = PlayerData.get(player).getContractsData();
        ItemStackHandler stackHandler = contractsData.getStackHandler();

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
    public boolean canInteractWith(EntityPlayer playerIn) {
        return soulboundLectern.isUsableByPlayer(playerIn);
    }
}