package aurocosh.divinefavor.common.network.message.sever.stack_properties

import net.minecraft.util.EnumFacing

class MessageSyncPropertyEnumFacing : MessageSyncProperty<EnumFacing> {
    override var value: EnumFacing = EnumFacing.UP

    constructor()
    constructor(itemId: Int, propertyName: String, value: EnumFacing) : super(itemId, propertyName) {
        this.value = value
    }
}
