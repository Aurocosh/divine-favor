package aurocosh.divinefavor.common.network.message.sever

class MessageSyncTalismanPropertyBool : MessageSyncTalismanProperty<Boolean> {
    override var value: Boolean = false

    constructor()

    constructor(name: String, value: Boolean) : super(name) {
        this.value = value
    }
}
