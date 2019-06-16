package aurocosh.divinefavor.common.network.message.sever.talisman_properties

import net.minecraft.util.EnumFacing

class MessageSyncTalismanPropertyEnumFacing : MessageSyncTalismanProperty<EnumFacing> {
    override var value: EnumFacing = EnumFacing.UP

    constructor()

    constructor(name: String, value: EnumFacing) : super(name) {
        this.value = value
    }
}
