package aurocosh.divinefavor.common.item.talisman

import aurocosh.divinefavor.common.network.message.sever.talisman_properties.*
import aurocosh.divinefavor.common.stack_properties.*
import net.minecraft.block.state.IBlockState
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos

class TalismanPropertyHandler(parentName: String) : StackPropertyHandler(parentName) {
    override fun getSynchronizerInt() = { itemId: Int, property: StackProperty<Int>, value: Int ->
        MessageSyncTalismanPropertyInt(itemId, property.name, value).send()
    }

    override fun getSynchronizerFloat() = { itemId: Int, property: StackProperty<Float>, value: Float ->
        MessageSyncTalismanPropertyFloat(itemId, property.name, value).send()
    }

    override fun getSynchronizerBool() = { itemId: Int, property: StackProperty<Boolean>, value: Boolean ->
        MessageSyncTalismanPropertyBool(itemId, property.name, value).send()
    }

    override fun getSynchronizerEnumFacing() = { itemId: Int, property: StackProperty<EnumFacing>, value: EnumFacing ->
        MessageSyncTalismanPropertyEnumFacing(itemId, property.name, value).send()
    }

    override fun getSynchronizerBlockPos() = { itemId: Int, property: StackProperty<BlockPos>, value: BlockPos ->
        MessageSyncTalismanPropertyBlockPos(itemId, property.name, value).send()
    }

    override fun getSynchronizerIBlockState() = { itemId: Int, property: StackProperty<IBlockState>, value: IBlockState ->
        MessageSyncTalismanPropertyIBlockState(itemId, property.name, value).send()
    }
}