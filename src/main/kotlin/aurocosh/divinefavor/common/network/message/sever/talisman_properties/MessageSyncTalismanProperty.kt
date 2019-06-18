package aurocosh.divinefavor.common.network.message.sever.talisman_properties

import aurocosh.divinefavor.common.item.talisman.ITalismanContainer
import aurocosh.divinefavor.common.network.message.sever.stack_properties.MessageSyncProperty
import aurocosh.divinefavor.common.stack_properties.IPropertyContainer
import aurocosh.divinefavor.common.stack_properties.StackProperty
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.Item

abstract class MessageSyncTalismanProperty<T> : MessageSyncProperty<T> {
    constructor()
    constructor(itemId: Int, name: String) : super(itemId, name)

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val talisman = Item.getItemById(itemId) ?: return
        if (talisman !is IPropertyContainer)
            return

        val (_, stack) = UtilPlayer.findStackInInventory(serverPlayer) {
            val item = it.item
            item is ITalismanContainer && item.getTalismanStack(it).item == talisman
        }

        if (!talisman.properties.exist(name))
            return

        val property = talisman.properties.get(name) as? StackProperty<T> ?: return
        property.setValue(stack, value)
    }
}
