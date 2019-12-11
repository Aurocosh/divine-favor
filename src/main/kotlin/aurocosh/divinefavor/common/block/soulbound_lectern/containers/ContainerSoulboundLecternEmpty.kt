package aurocosh.divinefavor.common.block.soulbound_lectern.containers

import aurocosh.divinefavor.common.block.soulbound_lectern.TileSoulboundLectern
import aurocosh.divinefavor.common.item.base.GenericContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class ContainerSoulboundLecternEmpty(player: EntityPlayer, private val soulboundLectern: TileSoulboundLectern) : GenericContainer(1) {
    init {
        val shardHandler = this.soulboundLectern.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
        this.addSlotToContainer(SlotItemHandler(shardHandler, 0, 80, 36))

        generateInventorySlots(player.inventory, 8, 84)
        generateHotbarSlots(player.inventory, 8, 142)
    }

    override fun canInteractWith(player: EntityPlayer): Boolean {
        return soulboundLectern.isUsableByPlayer(player)
    }
}