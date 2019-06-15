package aurocosh.divinefavor.common.network.message.sever

import aurocosh.divinefavor.common.talisman_properties.TalismanPropertyInt
import net.minecraft.entity.player.EntityPlayerMP

class MessageSyncTalismanPropertyInt : MessageSyncTalismanProperty {
    var value: Int = 0

    constructor()

    constructor(name: String, value: Int) : super(name) {
        this.value = value
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val (talismanStack, stackProperty) = getProperty(serverPlayer) ?: return
        val property = stackProperty as TalismanPropertyInt
        property.setValue(talismanStack, value)
    }
}
