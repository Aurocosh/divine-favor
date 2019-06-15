package aurocosh.divinefavor.common.network.message.sever

import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import aurocosh.divinefavor.common.item.talisman.properties.StackPropertyInt
import aurocosh.divinefavor.common.item.talisman_tools.TalismanContainerAdapter
import net.minecraft.entity.player.EntityPlayerMP

class MessageSyncTalismanPropertyInt : DivineServerMessage {
    var playerSlotIndex: Int = 0
    var name: String = ""
    var value: Int = 0

    constructor()

    constructor(playerSlotIndex: Int, tag: String, value: Int) : super() {
        this.playerSlotIndex = playerSlotIndex
        this.name = tag
        this.value = value
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val stack = serverPlayer.inventory.getStackInSlot(playerSlotIndex)
        val talismanStack = TalismanContainerAdapter.getTalismanStack(stack) ?: return
        val item = talismanStack.item as? ItemTalisman ?: return
        val property = item.properties.get(name) as? StackPropertyInt ?: return
        property.setValue(talismanStack, value)
    }
}
