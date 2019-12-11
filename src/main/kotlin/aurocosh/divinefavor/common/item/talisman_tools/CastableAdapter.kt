package aurocosh.divinefavor.common.item.talisman_tools

import aurocosh.divinefavor.common.item.base.ICastable
import aurocosh.divinefavor.common.item.talisman.ISelectedStackProvider
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.network.message.sever.syncing.MessageSyncTalismanContainerSlot
import aurocosh.divinefavor.common.stack_actions.StackAction
import aurocosh.divinefavor.common.stack_actions.StackActionHandler
import aurocosh.divinefavor.common.stack_actions.interfaces.IActionContainer
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.stack_properties.interfaces.IStackPropertyContainer
import aurocosh.divinefavor.common.stack_properties.properties.base.StackProperty
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.common.capabilities.Capability

data class CastableStackWrapper<T : ICastable>(val stack: ItemStack, val talisman: T)

object CastableAdapter {
    fun selectSlot(playerSlot: Int, talismanSlot: Int) {
        MessageSyncTalismanContainerSlot(playerSlot, talismanSlot).send()
    }

    inline fun <reified T : ICastable> getCastableStack(stack: ItemStack): CastableStackWrapper<T>? {
        val item = stack.item
        if (item !is ISelectedStackProvider)
            return null
        val talismanStack = item.getSelectedStack(stack)
        if (talismanStack.isEmpty)
            return null
        val talisman = talismanStack.item as? T ?: return null
        return CastableStackWrapper(talismanStack, talisman)
    }

    fun <T : IStackContainer> findProperty(stack: ItemStack, item: Item, propertyName: String, container: Item, propertyHandler: StackPropertyHandler, capability: Capability<T>): Pair<ItemStack, StackProperty<out Any>>? {
        if (item == container) {
            val property = propertyHandler[propertyName] ?: return null
            return Pair(stack, property)
        }

        val handler = stack.cap(capability)
        val selectedStack = handler.getSelectedStack()

        val selectedItem = selectedStack.item
        if (selectedItem !is IStackPropertyContainer)
            return null
        return selectedItem.findProperty(selectedStack, item, propertyName)
    }

    fun <T : IStackContainer> findAction(stack: ItemStack, item: Item, actionName: String, container: Item, actionHandler: StackActionHandler, capability: Capability<T>): Pair<ItemStack, StackAction>? {
        if (item == container) {
            val property = actionHandler[actionName] ?: return null
            return Pair(stack, property)
        }

        val handler = stack.cap(capability)
        val selectedStack = handler.getSelectedStack()

        val selectedItem = selectedStack.item
        if (selectedItem !is IActionContainer)
            return null
        return selectedItem.findAction(selectedStack, item, actionName)
    }
}
