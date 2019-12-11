package aurocosh.divinefavor.common.stack_properties.interfaces

import aurocosh.divinefavor.common.stack_properties.properties.base.StackProperty
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

interface IStackPropertyContainer {
    val properties: IStackPropertyAccessor
    fun findProperty(stack: ItemStack, item: Item, propertyName: String): Pair<ItemStack, StackProperty<out Any>>? {
        if (item != this)
            return null
        val property = properties.get(propertyName) ?: return null
        return Pair(stack, property)
    }
}