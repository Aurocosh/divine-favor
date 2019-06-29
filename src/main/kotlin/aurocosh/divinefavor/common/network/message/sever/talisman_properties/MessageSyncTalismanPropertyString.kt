package aurocosh.divinefavor.common.network.message.sever.talisman_properties

class MessageSyncTalismanPropertyString : MessageSyncTalismanProperty<String> {
    override var value: String = ""

    constructor()

    constructor(itemId: Int, name: String, value: String) : super(itemId, name) {
        this.value = value
    }
}
