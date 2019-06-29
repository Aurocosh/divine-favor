package aurocosh.divinefavor.common.network.message.sever.syncing

import aurocosh.divinefavor.common.item.memory_pouch.ItemMemoryPouch
import aurocosh.divinefavor.common.item.memory_pouch.capability.MemoryPouchDataHandler.CAPABILITY_MEMORY_POUCH
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import net.minecraft.entity.player.EntityPlayerMP

class MessageSyncMemoryPouchSlot : DivineServerMessage {
    var playerSlotIndex: Int = 0
    var selectedSlotIndex: Int = 0

    constructor()

    constructor(playerSlotIndex: Int, selectedSlotIndex: Int) {
        this.playerSlotIndex = playerSlotIndex
        this.selectedSlotIndex = selectedSlotIndex
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val stack = serverPlayer.inventory.getStackInSlot(playerSlotIndex)
        if(stack.item !is ItemMemoryPouch)
            return
        val pouch = stack.cap(CAPABILITY_MEMORY_POUCH)
        pouch.selectedSlotIndex = selectedSlotIndex
    }
}
