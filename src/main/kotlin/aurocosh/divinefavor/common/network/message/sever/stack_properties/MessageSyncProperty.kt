package aurocosh.divinefavor.common.network.message.sever.stack_properties

import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import aurocosh.divinefavor.common.stack_properties.interfaces.IPropertyContainer
import aurocosh.divinefavor.common.stack_properties.properties.base.StackProperty
import aurocosh.divinefavor.common.util.HeldStack
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

abstract class MessageSyncProperty<T : Any> : DivineServerMessage {
    var itemId = -1
    var propertyName = ""
    abstract var value: T

    constructor()

    @Suppress("LeakingThis")
    constructor(itemId: Int, propertyName: String, value: T) : super() {
        this.itemId = itemId
        this.propertyName = propertyName
        this.value = value
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val item = Item.getItemById(itemId) ?: return
        val heldStacks = UtilPlayer.getHeldStacks(serverPlayer).toList()
        val map = heldStacks
                .map { this.findProperty(it, item) }
        val filterNotNull = map
                .filterNotNull()
        val (stack, property) = filterNotNull
                .firstOrNull() ?: return
        setProperty(stack, property)
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun setProperty(stack: ItemStack, property: StackProperty<out Any>) {
        val stackProperty = property as StackProperty<T>
        stackProperty.setValue(stack, value)
    }

    private fun findProperty(heldStack: HeldStack, item: Item): Pair<ItemStack, StackProperty<out Any>>? {
        val stack = heldStack.stack
        val container = stack.item as? IPropertyContainer ?: return null
        return container.findProperty(stack, item, propertyName)
    }
}
