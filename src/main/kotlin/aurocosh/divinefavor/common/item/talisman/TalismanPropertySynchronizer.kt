package aurocosh.divinefavor.common.item.talisman

import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanPropertyBlockPos
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanPropertyBool
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanPropertyIBlockState
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanPropertyInt
import aurocosh.divinefavor.common.stack_properties.StackProperty
import net.minecraft.block.state.IBlockState
import net.minecraft.util.math.BlockPos

object TalismanPropertySynchronizer {
    fun syncInt(property: StackProperty<Int>, value: Int){
        MessageSyncTalismanPropertyInt(property.name, value).send()
    }

    fun syncBool(property: StackProperty<Boolean>, value: Boolean){
        MessageSyncTalismanPropertyBool(property.name, value).send()
    }

    fun syncBlockPos(property: StackProperty<BlockPos>, value: BlockPos){
        MessageSyncTalismanPropertyBlockPos(property.name, value).send()
    }

    fun syncIBlockState(property: StackProperty<IBlockState>, value: IBlockState){
        MessageSyncTalismanPropertyIBlockState(property.name, value).send()
    }
}