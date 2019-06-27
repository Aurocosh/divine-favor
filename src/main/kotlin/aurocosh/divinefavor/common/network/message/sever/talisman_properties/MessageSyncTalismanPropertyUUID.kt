package aurocosh.divinefavor.common.network.message.sever.talisman_properties

import aurocosh.divinefavor.common.lib.EmptyConst.emptyUUID
import java.util.*

class MessageSyncTalismanPropertyUUID : MessageSyncTalismanProperty<UUID> {
    override var value: UUID = emptyUUID()

    constructor()

    constructor(itemId: Int, name: String, value: UUID) : super(itemId, name) {
        this.value = value
    }
}
