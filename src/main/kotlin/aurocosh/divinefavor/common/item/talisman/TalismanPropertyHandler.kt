package aurocosh.divinefavor.common.item.talisman

import aurocosh.divinefavor.common.network.message.sever.talisman_properties.*
import aurocosh.divinefavor.common.stack_properties.*
import net.minecraft.block.state.IBlockState
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos

class TalismanPropertyHandler(parentName: String) : StackPropertyHandler(parentName) {
    override fun getSynchronizerInt() = { property: StackProperty<Int>, value: Int ->
        MessageSyncTalismanPropertyInt(property.name, value).send()
    }

    override fun getSynchronizerBool() = { property: StackProperty<Boolean>, value: Boolean ->
        MessageSyncTalismanPropertyBool(property.name, value).send()
    }

    override fun getSynchronizerEnumFacing() = { property: StackProperty<EnumFacing>, value: EnumFacing ->
        MessageSyncTalismanPropertyEnumFacing(property.name, value).send()
    }

    override fun getSynchronizerBlockPos() = { property: StackProperty<BlockPos>, value: BlockPos ->
        MessageSyncTalismanPropertyBlockPos(property.name, value).send()
    }

    override fun getSynchronizerIBlockState() = { property: StackProperty<IBlockState>, value: IBlockState ->
        MessageSyncTalismanPropertyIBlockState(property.name, value).send()
    }
}