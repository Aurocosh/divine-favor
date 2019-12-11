package aurocosh.divinefavor.common.block.soulbound_lectern.containers

import aurocosh.divinefavor.common.block.soulbound_lectern.TileSoulboundLectern
import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritData
import aurocosh.divinefavor.common.item.base.GenericContainer
import net.minecraft.entity.player.EntityPlayer

class ContainerSoulboundLecternWithShard(player: EntityPlayer, private val soulboundLectern: TileSoulboundLectern) : GenericContainer(SpiritData.CONTRACT_SLOT_COUNT) {

    init {
        generateInventorySlots(player.inventory, 8, 84)
        generateHotbarSlots(player.inventory, 8, 142)
    }

    override fun canInteractWith(player: EntityPlayer): Boolean {
        return soulboundLectern.isUsableByPlayer(player)
    }
}