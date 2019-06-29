package aurocosh.divinefavor.common.network.message.sever.stack_properties

class MessageSyncPropertyString : MessageSyncProperty<String> {
    override var value: String = ""

    constructor()

    constructor(itemId: Int, name: String, value: String) : super(itemId, name) {
        this.value = value
    }
}
