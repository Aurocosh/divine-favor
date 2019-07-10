package aurocosh.divinefavor.common.item.talisman_tools

import aurocosh.divinefavor.common.item.talisman.ITalismanStackContainer
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.network.message.sever.syncing.MessageSyncTalismanContainerSlot
import aurocosh.divinefavor.common.stack_actions.StackAction
import aurocosh.divinefavor.common.stack_actions.StackActionHandler
import aurocosh.divinefavor.common.stack_actions.interfaces.IActionContainer
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.stack_properties.interfaces.IPropertyContainer
import aurocosh.divinefavor.common.stack_properties.properties.base.StackProperty
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.common.capabilities.Capability

data class TalismanStackWrapper<T : ItemTalisman>(val stack: ItemStack, val talisman: T)

object TalismanAdapter {
    fun selectSlot(playerSlot: Int, talismanSlot: Int) {
        MessageSyncTalismanContainerSlot(playerSlot, talismanSlot).send()
    }

    inline fun <reified T : ItemTalisman> getTalisman(stack: ItemStack): TalismanStackWrapper<T>? {
        val item = stack.item
        if (item !is ITalismanStackContainer)
            return null
        val talismanStack = item.getTalismanStack(stack)
        if (talismanStack.isEmpty)
            return null
        val talisman = talismanStack.item as? T ?: return null
        return TalismanStackWrapper(talismanStack, talisman)
    }

    fun <T : ITalismanTool> findProperty(stack: ItemStack, item: Item, propertyName: String, container: Item, propertyHandler: StackPropertyHandler, capability: Capability<T>): Pair<ItemStack, StackProperty<out Any>>? {
        if (item == container) {
            val property = propertyHandler[propertyName] ?: return null
            return Pair(stack, property)
        }

        val handler = stack.cap(capability)
        val selectedStack = handler.getSelectedStack()

        val selectedItem = selectedStack.item
        if (selectedItem !is IPropertyContainer)
            return null
        return selectedItem.findProperty(selectedStack, item, propertyName)
    }

    fun <T : ITalismanTool> findAction(stack: ItemStack, item: Item, actionName: String, container: Item, actionHandler: StackActionHandler, capability: Capability<T>): Pair<ItemStack, StackAction>? {
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
