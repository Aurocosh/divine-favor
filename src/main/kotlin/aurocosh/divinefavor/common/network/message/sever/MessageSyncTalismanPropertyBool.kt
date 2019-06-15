package aurocosh.divinefavor.common.network.message.sever

import aurocosh.divinefavor.common.talisman_properties.TalismanPropertyBool
import net.minecraft.entity.player.EntityPlayerMP

class MessageSyncTalismanPropertyBool : MessageSyncTalismanProperty {
    var value: Boolean = false

    constructor()

    constructor(name: String, value: Boolean) : super(name) {
        this.value = value
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val (talismanStack, stackProperty) = getProperty(serverPlayer) ?: return
        val property = stackProperty as TalismanPropertyBool
        property.setValue(talismanStack, value)
    }
}
