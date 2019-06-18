package aurocosh.divinefavor.common.network.message.sever.talisman_properties

import aurocosh.divinefavor.common.item.talisman.ITalismanContainer
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.item.talisman_tools.ItemTalismanContainer
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
        val stack = UtilPlayer.getItemInHand(serverPlayer) { it is ITalismanContainer }
        if(stack.isEmpty)
            return

        val container = stack.item as ITalismanContainer
        val talismanStack = container.getTalismanStack(stack)
        if(talismanStack.isEmpty)
            return

        val talisman = talismanStack.item as ItemTalisman
        if(!talisman.properties.exist(name))
            return

        val property = talisman.properties.get(name) as? StackProperty<T> ?: return
        property.setValue(stack, value)
    }
}
