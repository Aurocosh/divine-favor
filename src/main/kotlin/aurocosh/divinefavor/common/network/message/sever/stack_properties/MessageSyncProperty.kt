package aurocosh.divinefavor.common.network.message.sever.stack_properties

import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import aurocosh.divinefavor.common.stack_properties.interfaces.IPropertyContainer
import aurocosh.divinefavor.common.stack_properties.properties.StackProperty
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.Item

abstract class MessageSyncProperty<T> : DivineServerMessage {
    var itemId = -1
    var name: String = ""
    abstract var value: T

    constructor()

    constructor(itemId: Int, name: String) : super() {
        this.itemId = itemId
        this.name = name
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val item = Item.getItemById(itemId) ?: return
        if (item !is IPropertyContainer)
            return

        val stack = UtilPlayer.getItemInHand(serverPlayer) { it === item }
        if (stack.isEmpty)
            return

        if (!item.properties.exist(name))
            return

        val property = item.properties.get(name) as? StackProperty<T>
                ?: return
        property.setValue(stack, value)
    }
}
