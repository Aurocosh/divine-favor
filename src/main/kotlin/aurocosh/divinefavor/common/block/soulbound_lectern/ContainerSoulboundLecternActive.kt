package aurocosh.divinefavor.common.block.soulbound_lectern

import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritData
import aurocosh.divinefavor.common.item.base.GenericContainer
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import net.minecraft.entity.player.EntityPlayer

class ContainerSoulboundLecternActive(player: EntityPlayer, private val soulboundLectern: TileSoulboundLectern) : GenericContainer(SpiritData.CONTRACT_SLOT_COUNT) {
    private val spiritData: SpiritData = player.divinePlayerData.spiritData

    init {
        val spiritId = soulboundLectern.spiritId
        if (spiritId != -1) {
            val stackHandler = spiritData.getContractHandler(spiritId)

            var nextSlotIndex = 0
            nextSlotIndex = generateCustomSlotsGrid(stackHandler, 8, 18, 1, 4, nextSlotIndex)
            nextSlotIndex = generateCustomSlotsGrid(stackHandler, 98, 18, 1, 4, nextSlotIndex)
            nextSlotIndex = generateCustomSlotsGrid(stackHandler, 26, 36, 1, 3, nextSlotIndex)
            nextSlotIndex = generateCustomSlotsGrid(stackHandler, 98, 36, 1, 3, nextSlotIndex)
            nextSlotIndex = generateCustomSlotsGrid(stackHandler, 8, 54, 1, 4, nextSlotIndex)
            nextSlotIndex = generateCustomSlotsGrid(stackHandler, 98, 54, 1, 4, nextSlotIndex)
        }

        generateInventorySlots(player.inventory, 8, 84)
        generateHotbarSlots(player.inventory, 8, 142)
    }

    override fun canInteractWith(player: EntityPlayer): Boolean {
        return soulboundLectern.isUsableByPlayer(player)
    }

    override fun onContainerClosed(player: EntityPlayer) {
        super.onContainerClosed(player)
        spiritData.refreshContracts()
    }
}