package aurocosh.divinefavor.common.network.message.sever

import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks

class MessageSyncTalismanPropertyIBlockState : MessageSyncTalismanProperty<IBlockState> {
    override var value: IBlockState = Blocks.AIR.defaultState

    constructor()

    constructor(name: String, value: IBlockState) : super(name) {
        this.value = value
    }
}
