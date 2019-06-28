package aurocosh.divinefavor.common.stack_properties

import aurocosh.divinefavor.common.lib.IIndexedEnum
import aurocosh.divinefavor.common.network.message.sever.talisman_properties.*
import aurocosh.divinefavor.common.stack_properties.properties.StackProperty
import net.minecraft.block.state.IBlockState
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import java.util.*

open class TalismanPropertyGenerator() : StackPropertyGenerator() {
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

    override fun getSynchronizerUUID() = { itemId: Int, property: StackProperty<UUID>, value: UUID ->
        MessageSyncTalismanPropertyUUID(itemId, property.name, value).send()
    }

    override fun <T> getSynchronizerEnum(): (Int, StackProperty<T>, T) -> Unit where T : Enum<T> {
        return { itemId: Int, property: StackProperty<T>, value: T ->
            MessageSyncTalismanPropertyEnum(itemId, property.name, value.ordinal).send()
        }
    }
}