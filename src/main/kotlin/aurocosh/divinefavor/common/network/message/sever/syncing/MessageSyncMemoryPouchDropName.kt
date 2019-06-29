package aurocosh.divinefavor.common.network.message.sever.syncing

import aurocosh.divinefavor.common.item.ItemMemoryDrop
import aurocosh.divinefavor.common.item.memory_pouch.ItemMemoryPouch
import aurocosh.divinefavor.common.item.memory_pouch.capability.MemoryPouchDataHandler.CAPABILITY_MEMORY_POUCH
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.lib.extensions.set
import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import net.minecraft.entity.player.EntityPlayerMP

class MessageSyncMemoryPouchDropName : DivineServerMessage {
    var playerSlotIndex: Int = 0
    var selectedSlotIndex: Int = 0
    var templateName: String = ""

    constructor()

    constructor(playerSlotIndex: Int, selectedSlotIndex: Int, templateName: String) {
        this.playerSlotIndex = playerSlotIndex
        this.selectedSlotIndex = selectedSlotIndex
        this.templateName = templateName
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val stack = serverPlayer.inventory.getStackInSlot(playerSlotIndex)
        if (stack.item !is ItemMemoryPouch)
            return
        val pouch = stack.cap(CAPABILITY_MEMORY_POUCH)
        val stackInSlot = pouch.getStackInSlot(selectedSlotIndex)
        stackInSlot.set(ItemMemoryDrop.templateName, templateName)
    }
}
