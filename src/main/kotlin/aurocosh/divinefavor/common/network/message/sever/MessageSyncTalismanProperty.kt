package aurocosh.divinefavor.common.network.message.sever

import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import aurocosh.divinefavor.common.talisman_properties.TalismanProperty
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack

data class PropertyData(val stack: ItemStack, val property: TalismanProperty<out Any>);

abstract class MessageSyncTalismanProperty : DivineServerMessage {
    var name: String = ""

    constructor()

    constructor(name: String) : super() {
        this.name = name
    }

    protected fun getProperty(playerMP: EntityPlayerMP): PropertyData? {
        val stack = UtilPlayer.getItemInHand(playerMP) { it is ItemTalisman && it.properties.exist(name) }
        val talisman = stack.item as ItemTalisman
        val property = talisman.properties.get(name) ?: return null
        return PropertyData(stack, property)
    }
}
