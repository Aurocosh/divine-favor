package aurocosh.divinefavor.common.network.message.sever

import aurocosh.divinefavor.common.item.talisman_tools.TalismanAdapter
import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import net.minecraft.entity.player.EntityPlayerMP

class MessageSyncTalismanContainerSlot : DivineServerMessage {
    var playerSlotIndex: Int = 0
    var selectedGrimoireIndex: Int = 0

    constructor()

    constructor(playerSlotIndex: Int, selectedGrimoireIndex: Int) {
        this.playerSlotIndex = playerSlotIndex
        this.selectedGrimoireIndex = selectedGrimoireIndex
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val stack = serverPlayer.inventory.getStackInSlot(playerSlotIndex)
        val talismanContainer = TalismanAdapter.getTalismanContainer(stack)
        if (talismanContainer != null)
            talismanContainer.selectedSlotIndex = selectedGrimoireIndex
    }
}
