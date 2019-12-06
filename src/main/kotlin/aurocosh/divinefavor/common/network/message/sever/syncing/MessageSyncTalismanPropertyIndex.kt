package aurocosh.divinefavor.common.network.message.sever.syncing

import aurocosh.divinefavor.common.item.talisman.ISelectedStackProvider
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import net.minecraft.entity.player.EntityPlayerMP

class MessageSyncTalismanPropertyIndex : DivineServerMessage {
    var playerSlotIndex: Int = 0
    var index: Int = 0

    constructor()

    constructor(playerSlotIndex: Int, index: Int) : super() {
        this.playerSlotIndex = playerSlotIndex
        this.index = index
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val stack = serverPlayer.inventory.getStackInSlot(playerSlotIndex)
        val container = stack.item as? ISelectedStackProvider ?: return
        val talismanStack = container.getSelectedStack(stack)
        val item = talismanStack.item as? ItemTalisman ?: return
        item.properties.setSelectedIndex(stack, index)
    }
}
