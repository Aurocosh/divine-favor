package aurocosh.divinefavor.common.network.message.sever.stack_properties

class MessageSyncPropertyFloat : MessageSyncProperty<Float> {
    override var value: Float = 0f

    constructor()
    constructor(itemId: Int, propertyName: String, value: Float) : super(itemId, propertyName) {
        this.value = value
    }
}
