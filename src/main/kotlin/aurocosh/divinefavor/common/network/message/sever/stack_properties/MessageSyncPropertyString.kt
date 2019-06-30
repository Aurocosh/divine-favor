package aurocosh.divinefavor.common.network.message.sever.stack_properties

class MessageSyncPropertyString : MessageSyncProperty<String> {
    override lateinit var value: String

    constructor()
    constructor(itemId: Int, propertyName: String, value: String) : super(itemId, propertyName, value)
}
