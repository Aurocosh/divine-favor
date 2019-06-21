package aurocosh.divinefavor.common.network.message.sever.talisman_properties

class MessageSyncTalismanPropertyFloat : MessageSyncTalismanProperty<Float> {
    override var value: Float = 0f

    constructor()

    constructor(itemId: Int, name: String, value: Float) : super(itemId, name) {
        this.value = value
    }
}
