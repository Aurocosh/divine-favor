package aurocosh.divinefavor.common.network.message.sever.stack_properties

import aurocosh.divinefavor.common.lib.EmptyConst.invalidUUID
import java.util.*

class MessageSyncPropertyUUID : MessageSyncProperty<UUID> {
    override var value: UUID = invalidUUID()

    constructor()
    constructor(itemId: Int, propertyName: String, value: UUID) : super(itemId, propertyName) {
        this.value = value
    }
}
