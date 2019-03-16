package aurocosh.divinefavor.common.block.medium;

import aurocosh.divinefavor.common.item.base.GenericContainer;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nonnull;

public class ContainerMediumWithStone extends GenericContainer {
    public TileMedium ironMedium;

    public ContainerMediumWithStone(EntityPlayer player, TileMedium ironMedium) {
        super(TileMedium.SIZE);
        this.ironMedium = ironMedium;

        generateCustomSlotsGrid(ironMedium.getLeftStackHandler(), 8, 18, 3, 3, 0);
        generateCustomSlotsGrid(ironMedium.getRightStackHandler(), 116, 18, 3, 3, 0);

        generateInventorySlots(player.inventory, 8, 84);
        generateHotbarSlots(player.inventory, 8, 142);
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        return ironMedium.isUsableByPlayer(player);
    }
}