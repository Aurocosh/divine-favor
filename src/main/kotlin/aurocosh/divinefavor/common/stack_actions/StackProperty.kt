package aurocosh.divinefavor.common.stack_actions

import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.network.message.sever.MessagePerformStackAction
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class StackAction(val name: String, val orderIndex: Int) {
    val tooltipKey = ResourceNamer.getTypedNameString("tooltip", "property", name)
    val displayKey = ResourceNamer.getTypedNameString("name", "property", name)

    private val clientListeners: MutableSet<(EntityPlayer, ItemStack) -> Unit> = HashSet()
    fun addClientListener(listener: (EntityPlayer, ItemStack) -> Unit) = clientListeners.add(listener)

    private val serverListeners: MutableSet<(EntityPlayer, ItemStack) -> Unit> = HashSet()
    fun addServerListener(listener: (EntityPlayer, ItemStack) -> Unit) = serverListeners.add(listener)

    fun activateServer(player: EntityPlayer, stack: ItemStack) {
        serverListeners.forEach { it.invoke(player, stack) }
    }

    fun activateClient(player: EntityPlayer, stack: ItemStack) {
        clientListeners.forEach { it.invoke(player, stack) }
        if (serverListeners.isNotEmpty()) {
            val itemId = Item.getIdFromItem(stack.item)
            MessagePerformStackAction(itemId, name).send()
        }
    }
}

