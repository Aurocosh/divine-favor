package aurocosh.divinefavor.common.network.message.sever

import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import aurocosh.divinefavor.common.item.talisman_tools.TalismanAdapter
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
        val talismanStack = TalismanAdapter.getTalismanStack(stack) ?: return
        val item = talismanStack.item as? ItemTalisman ?: return
        item.properties.setSelectedIndex(stack, index)
    }
}
