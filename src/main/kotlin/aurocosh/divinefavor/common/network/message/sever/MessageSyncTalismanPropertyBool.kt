package aurocosh.divinefavor.common.network.message.sever

import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import aurocosh.divinefavor.common.item.talisman.properties.TalismanPropertyBool
import aurocosh.divinefavor.common.item.talisman.properties.TalismanPropertyInt
import aurocosh.divinefavor.common.item.talisman_tools.TalismanContainerAdapter
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack

class MessageSyncTalismanPropertyBool : DivineServerMessage {
    var playerSlotIndex: Int = 0
    var name: String = ""
    var value: Boolean = false

    constructor()

    constructor(playerSlotIndex: Int, tag: String, value: Boolean) : super() {
        this.playerSlotIndex = playerSlotIndex
        this.name = tag
        this.value = value
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val stack = serverPlayer.inventory.getStackInSlot(playerSlotIndex)
        val talismanStack = TalismanContainerAdapter.getTalismanStack(stack) ?: return
        val item = talismanStack.item as? ItemTalisman ?: return
        val property = item.getProperty(name) as? TalismanPropertyBool ?: return
        property.setValue(talismanStack, value)
    }
}
