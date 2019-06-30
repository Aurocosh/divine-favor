package aurocosh.divinefavor.common.network.message.sever.stack_properties

import net.minecraft.block.state.IBlockState

class MessageSyncPropertyIBlockState : MessageSyncProperty<IBlockState> {
    override lateinit var value: IBlockState

    constructor()
    constructor(itemId: Int, propertyName: String, value: IBlockState) : super(itemId, propertyName, value)
}
