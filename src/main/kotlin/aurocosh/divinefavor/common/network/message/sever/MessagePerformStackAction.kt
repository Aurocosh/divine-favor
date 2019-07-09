package aurocosh.divinefavor.common.network.message.sever

import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import aurocosh.divinefavor.common.stack_actions.StackAction
import aurocosh.divinefavor.common.stack_actions.interfaces.IActionContainer
import aurocosh.divinefavor.common.util.HeldStack
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class MessagePerformStackAction : DivineServerMessage {
    var itemId = -1
    var actionName = ""

    constructor()

    constructor(itemId: Int, actionName: String) : super() {
        this.itemId = itemId
        this.actionName = actionName
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val item = Item.getItemById(itemId) ?: return
        val heldStacks = UtilPlayer.getHeldStacks(serverPlayer).toList()
        val actionPair = heldStacks
                .mapNotNull { findAction(it, item) }
                .firstOrNull()
        val (stack, action) = actionPair ?: return
        action.activateServer(serverPlayer, stack)
    }

    private fun findAction(heldStack: HeldStack, item: Item): Pair<ItemStack, StackAction>? {
        val stack = heldStack.stack
        val container = stack.item as? IActionContainer ?: return null
        return container.findAction(stack, item, actionName)
    }
}
