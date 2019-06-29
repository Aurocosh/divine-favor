package aurocosh.divinefavor.common.network.message.sever.talisman_properties

import aurocosh.divinefavor.common.item.talisman.ITalismanStackContainer
import aurocosh.divinefavor.common.lib.extensions.set
import aurocosh.divinefavor.common.network.message.sever.stack_properties.MessageSyncProperty
import aurocosh.divinefavor.common.stack_properties.interfaces.IPropertyContainer
import aurocosh.divinefavor.common.stack_properties.properties.StackProperty
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
            item is ITalismanStackContainer && item.getTalismanStack(it).item === talisman
        }

        val container = stack.item as ITalismanStackContainer
        val talismanStack = container.getTalismanStack(stack)

        if (!talisman.properties.exist(name))
            return

        val property = talisman.properties.get(name) as? StackProperty<T>
                ?: return
        talismanStack.set(property, value)
    }
}
