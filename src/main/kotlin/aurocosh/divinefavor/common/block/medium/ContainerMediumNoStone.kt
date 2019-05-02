package aurocosh.divinefavor.common.block.medium

import aurocosh.divinefavor.common.item.base.GenericContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.items.SlotItemHandler

class ContainerMediumNoStone(player: EntityPlayer, var ironMedium: TileMedium) : GenericContainer(TileMedium.SIZE) {
    init {
        addSlotToContainer(SlotItemHandler(ironMedium.stoneStackHandler, 0, 80, 36))

        generateCustomSlotsGrid(ironMedium.leftStackHandler, 8, 18, 3, 3, 0)
        generateCustomSlotsGrid(ironMedium.rightStackHandler, 116, 18, 3, 3, 0)

        generateInventorySlots(player.inventory, 8, 84)
        generateHotbarSlots(player.inventory, 8, 142)
    }

    override fun canInteractWith(player: EntityPlayer): Boolean {
        return ironMedium.isUsableByPlayer(player)
    }
}