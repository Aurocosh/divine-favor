package aurocosh.divinefavor.common.block.soulbound_lectern;

import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritData;
import aurocosh.divinefavor.common.item.base.GenericContainer;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nonnull;

public class ContainerSoulboundLecternWithShard extends GenericContainer {
    private final TileSoulboundLectern soulboundLectern;

    public ContainerSoulboundLecternWithShard(EntityPlayer player, TileSoulboundLectern soulboundLectern) {
        super(SpiritData.CONTRACT_SLOT_COUNT);
        this.soulboundLectern = soulboundLectern;

        generateInventorySlots(player.inventory, 8, 84);
        generateHotbarSlots(player.inventory, 8, 142);
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        return soulboundLectern.isUsableByPlayer(player);
    }
}