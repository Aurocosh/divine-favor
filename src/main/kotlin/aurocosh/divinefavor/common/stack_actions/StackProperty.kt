package aurocosh.divinefavor.common.stack_actions

import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.lib.Lambdas
import aurocosh.divinefavor.common.network.message.sever.MessagePerformStackAction
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class StackAction(val name: String, val clientAction: (EntityPlayer, ItemStack) -> Unit = Lambdas::emptyLambda, val serverAction: (EntityPlayer, ItemStack) -> Unit = Lambdas::emptyLambda, val orderIndex: Int = 0) {
    val tooltipKey = ResourceNamer.getTypedNameString("tooltip", "property", name)
    val displayKey = ResourceNamer.getTypedNameString("name", "property", name)

    fun activateServer(player: EntityPlayer, stack: ItemStack) {
        serverAction.invoke(player, stack)
    }

    fun activateClient(player: EntityPlayer, stack: ItemStack) {
        clientAction.invoke(player, stack)
        val itemId = Item.getIdFromItem(stack.item)
        MessagePerformStackAction(itemId, name).send()
    }
}

