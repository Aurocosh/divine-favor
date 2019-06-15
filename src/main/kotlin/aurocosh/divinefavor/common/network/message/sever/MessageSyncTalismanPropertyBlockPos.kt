package aurocosh.divinefavor.common.network.message.sever

import aurocosh.divinefavor.common.talisman_properties.TalismanPropertyBlockPos
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.math.BlockPos

class MessageSyncTalismanPropertyBlockPos : MessageSyncTalismanProperty {
    var value: BlockPos = BlockPos.ORIGIN

    constructor()

    constructor(name: String, value: BlockPos) : super(name) {
        this.value = value
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val (talismanStack, stackProperty) = getProperty(serverPlayer) ?: return
        val property = stackProperty as TalismanPropertyBlockPos
        property.setValue(talismanStack, value)
    }
}
