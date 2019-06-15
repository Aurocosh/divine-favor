package aurocosh.divinefavor.common.network.message.sever

class MessageSyncTalismanPropertyInt : MessageSyncTalismanProperty<Int> {
    override var value: Int = 0

    constructor()

    constructor(name: String, value: Int) : super(name) {
        this.value = value
    }
}
