package aurocosh.divinefavor.common.network.message.sever.talisman_properties

import net.minecraft.util.math.BlockPos

class MessageSyncTalismanPropertyBlockPos : MessageSyncTalismanProperty<BlockPos> {
    override var value: BlockPos = BlockPos.ORIGIN

    constructor()

    constructor(name: String, value: BlockPos) : super(name) {
        this.value = value
    }
}
