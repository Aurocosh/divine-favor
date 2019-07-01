package aurocosh.divinefavor.common.network.message.sever.stack_properties

import net.minecraft.util.math.BlockPos

class MessageSyncPropertyBlockPos : MessageSyncProperty<BlockPos> {
    override var value: BlockPos = BlockPos.ORIGIN

    constructor()
    constructor(itemId: Int, propertyName: String, value: BlockPos) : super(itemId, propertyName) {
        this.value = value
    }
}
