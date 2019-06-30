package aurocosh.divinefavor.common.stack_properties.generators

import aurocosh.divinefavor.common.network.message.sever.stack_properties.*
import aurocosh.divinefavor.common.stack_properties.properties.*
import aurocosh.divinefavor.common.stack_properties.properties.base.StackProperty
import net.minecraft.block.state.IBlockState
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import java.util.*

object StackPropertySynchronizer : IPropertySynchronizer {
    @Suppress("UNCHECKED_CAST")
    override fun <T : Any, K : (itemId: Int, property: StackProperty<T>, value: T) -> Unit> getSynchronizer(property: StackProperty<T>): K {
        return when (property) {
            is StackPropertyBlockPos -> this::syncBlockPos as K
            is StackPropertyBool -> this::syncBool as K
            is StackPropertyEnum -> this::syncEnum as K
            is StackPropertyEnumFacing -> this::syncEnumFacing as K
            is StackPropertyFloat -> this::syncFloat as K
            is StackPropertyIBlockState -> this::syncIBlockState as K
            is StackPropertyInt -> this::syncInt as K
            is StackPropertyString -> this::syncString as K
            is StackPropertyUUID -> this::syncUUID as K
            else -> TODO("Property sync of this type is not implemented")
        }
    }

    private fun syncBlockPos(itemId: Int, property: StackProperty<BlockPos>, value: BlockPos) = MessageSyncPropertyBlockPos(itemId, property.name, value).send()
    private fun syncBool(itemId: Int, property: StackProperty<Boolean>, value: Boolean) = MessageSyncPropertyBool(itemId, property.name, value).send()
    private fun syncEnum(itemId: Int, property: StackProperty<Enum<*>>, value: Enum<*>) = MessageSyncPropertyEnum(itemId, property.name, value.ordinal).send()
    private fun syncEnumFacing(itemId: Int, property: StackProperty<EnumFacing>, value: EnumFacing) = MessageSyncPropertyEnumFacing(itemId, property.name, value).send()
    private fun syncFloat(itemId: Int, property: StackProperty<Float>, value: Float) = MessageSyncPropertyFloat(itemId, property.name, value).send()
    private fun syncIBlockState(itemId: Int, property: StackProperty<IBlockState>, value: IBlockState) = MessageSyncPropertyIBlockState(itemId, property.name, value).send()
    private fun syncInt(itemId: Int, property: StackProperty<Int>, value: Int) = MessageSyncPropertyInt(itemId, property.name, value).send()
    private fun syncString(itemId: Int, property: StackProperty<String>, value: String) = MessageSyncPropertyString(itemId, property.name, value).send()
    private fun syncUUID(itemId: Int, property: StackProperty<UUID>, value: UUID) = MessageSyncPropertyUUID(itemId, property.name, value).send()
}