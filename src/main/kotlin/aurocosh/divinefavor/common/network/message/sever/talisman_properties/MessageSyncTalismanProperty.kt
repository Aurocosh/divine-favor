package aurocosh.divinefavor.common.network.message.sever.talisman_properties

import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import aurocosh.divinefavor.common.stack_properties.StackProperty
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack

data class PropertyData(val stack: ItemStack, val property: StackProperty<out Any>);

abstract class MessageSyncTalismanProperty<T> : DivineServerMessage {
    var name: String = ""
    abstract var value: T

    constructor()

    constructor(name: String) : super() {
        this.name = name
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val stack = UtilPlayer.getItemInHand(serverPlayer) { it is ItemTalisman && it.properties.exist(name) }
        val talisman = stack.item as ItemTalisman
        val property = talisman.properties.get(name) as? StackProperty<T> ?: return

        property.setValue(stack, value)
    }
}
