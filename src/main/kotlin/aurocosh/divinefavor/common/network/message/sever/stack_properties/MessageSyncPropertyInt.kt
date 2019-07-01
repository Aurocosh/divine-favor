package aurocosh.divinefavor.common.network.message.sever.stack_properties

class MessageSyncPropertyInt : MessageSyncProperty<Int> {
    override var value: Int = 0

    constructor()
    constructor(itemId: Int, propertyName: String, value: Int) : super(itemId, propertyName) {
        this.value = value
    }
}
