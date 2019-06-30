package aurocosh.divinefavor.common.network.message.sever.stack_properties

import java.util.*

class MessageSyncPropertyUUID : MessageSyncProperty<UUID> {
    override lateinit var value: UUID

    constructor()
    constructor(itemId: Int, propertyName: String, value: UUID) : super(itemId, propertyName, value)
}
