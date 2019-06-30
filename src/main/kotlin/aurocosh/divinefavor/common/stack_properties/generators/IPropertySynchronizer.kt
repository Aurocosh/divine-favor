package aurocosh.divinefavor.common.stack_properties.generators

import aurocosh.divinefavor.common.stack_properties.properties.base.StackProperty

interface IPropertySynchronizer {
    fun <T : Any, K : (itemId: Int, property: StackProperty<T>, value: T) -> Unit> getSynchronizer(property: StackProperty<T>): K
}