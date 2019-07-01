package aurocosh.divinefavor.common.network.message.sever.stack_properties

class MessageSyncPropertyBool : MessageSyncProperty<Boolean> {
    override var value: Boolean = false

    constructor()
    constructor(itemId: Int, propertyName: String, value: Boolean) : super(itemId, propertyName) {
        this.value = value
    }
}
