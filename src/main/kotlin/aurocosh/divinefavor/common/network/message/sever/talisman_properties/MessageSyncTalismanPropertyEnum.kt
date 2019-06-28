package aurocosh.divinefavor.common.network.message.sever.talisman_properties

import aurocosh.divinefavor.common.item.talisman.ITalismanStackContainer
import aurocosh.divinefavor.common.stack_properties.IPropertyContainer
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyEnum
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.Item

class MessageSyncTalismanPropertyEnum : MessageSyncTalismanProperty<Int> {
    override var value: Int = 0

    constructor()

    constructor(itemId: Int, name: String, value: Int) : super(itemId, name) {
        this.value = value
    }

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

        val property = talisman.properties.get(name) as? StackPropertyEnum
                ?: return
        property.setOrdinalValue(talismanStack, value)
    }
}
