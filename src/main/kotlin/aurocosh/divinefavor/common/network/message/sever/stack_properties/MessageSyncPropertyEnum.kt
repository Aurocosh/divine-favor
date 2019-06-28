package aurocosh.divinefavor.common.network.message.sever.stack_properties

import aurocosh.divinefavor.common.stack_properties.IPropertyContainer
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyEnum
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.Item

class MessageSyncPropertyEnum : MessageSyncProperty<Int> {
    override var value: Int = 0

    constructor()

    constructor(itemId: Int, name: String, value: Int) : super(itemId, name) {
        this.value = value
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

        val property = item.properties.get(name) as? StackPropertyEnum
                ?: return
        property.setOrdinalValue(stack, value)
    }
}
