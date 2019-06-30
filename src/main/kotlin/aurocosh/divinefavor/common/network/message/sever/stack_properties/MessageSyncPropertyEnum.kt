package aurocosh.divinefavor.common.network.message.sever.stack_properties

import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyEnum
import aurocosh.divinefavor.common.stack_properties.properties.base.StackProperty
import net.minecraft.item.ItemStack

class MessageSyncPropertyEnum : MessageSyncProperty<Int> {
    override var value: Int = 0

    constructor()
    constructor(itemId: Int, propertyName: String, value: Int) : super(itemId, propertyName, value)

    override fun setProperty(stack: ItemStack, property: StackProperty<out Any>) {
        val propertyEnum = property as StackPropertyEnum
        propertyEnum.setOrdinalValue(stack,value)
    }
}
