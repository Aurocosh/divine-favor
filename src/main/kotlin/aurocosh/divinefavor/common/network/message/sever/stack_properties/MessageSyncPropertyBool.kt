package aurocosh.divinefavor.common.network.message.sever.stack_properties

class MessageSyncPropertyBool : MessageSyncProperty<Boolean> {
    override var value: Boolean = false

    constructor()

    constructor(itemId: Int, name: String, value: Boolean) : super(itemId, name) {
        this.value = value
    }
}
