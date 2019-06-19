package aurocosh.divinefavor.common.network.message.sever.stack_properties

import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks

class MessageSyncPropertyIBlockState : MessageSyncProperty<IBlockState> {
    override var value: IBlockState = Blocks.AIR.defaultState

    constructor()

    constructor(itemId: Int, name: String, value: IBlockState) : super(itemId, name) {
        this.value = value
    }
}
