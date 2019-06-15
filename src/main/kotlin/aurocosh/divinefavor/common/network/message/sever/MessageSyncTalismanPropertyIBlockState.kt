package aurocosh.divinefavor.common.network.message.sever

import aurocosh.divinefavor.common.talisman_properties.TalismanPropertyIBlockState
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Blocks

class MessageSyncTalismanPropertyIBlockState : MessageSyncTalismanProperty {
    var value: IBlockState = Blocks.AIR.defaultState

    constructor()

    constructor(name: String, value: IBlockState) : super(name) {
        this.value = value
    }

    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        val (talismanStack, stackProperty) = getProperty(serverPlayer) ?: return
        val property = stackProperty as TalismanPropertyIBlockState
        property.setValue(talismanStack, value)
    }
}
