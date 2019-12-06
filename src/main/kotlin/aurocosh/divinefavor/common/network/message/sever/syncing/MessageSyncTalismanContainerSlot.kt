package aurocosh.divinefavor.common.network.message.sever.syncing

import aurocosh.divinefavor.common.item.talisman.IStackContainerProvider
import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import net.minecraft.entity.player.EntityPlayerMP

class MessageSyncTalismanContainerSlot : DivineServerMessage {
    var playerSlotIndex: Int = 0
    var selectedToolIndex: Int = 0

    constructor()

    constructor(playerSlotIndex: Int, selectedToolIndex: Int) {
        this.playerSlotIndex = playerSlotIndex
        this.selectedToolIndex = selectedToolIndex
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val stack = serverPlayer.inventory.getStackInSlot(playerSlotIndex)
        val toolContainer = stack.item as? IStackContainerProvider ?: return
        val talismanTool = toolContainer.getStackContainer(stack)
        talismanTool.selectedSlotIndex = selectedToolIndex
    }
}
