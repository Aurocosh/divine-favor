package aurocosh.divinefavor.common.stack_properties

import aurocosh.divinefavor.common.network.message.sever.stack_properties.*
import net.minecraft.block.state.IBlockState
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import java.util.*

open class StackPropertyGenerator() {
    fun makeIntProperty(name: String, defaultValue: Int, minValue: Int = 1, maxValue: Int = defaultValue, showInTooltip: Boolean = true, showInGui: Boolean = true, orderIndex: Int = 0): StackPropertyInt {
        return StackPropertyInt(name, defaultValue, minValue, maxValue, showInTooltip, showInGui, orderIndex, getSynchronizerInt())
    }

    fun makeFloatProperty(name: String, defaultValue: Float, minValue: Float = 1f, maxValue: Float = defaultValue, showInTooltip: Boolean = true, showInGui: Boolean = true, orderIndex: Int = 0): StackPropertyFloat {
        return StackPropertyFloat(name, defaultValue, minValue, maxValue, showInTooltip, showInGui, orderIndex, getSynchronizerFloat())
    }

    fun makeBoolProperty(name: String, defaultValue: Boolean, showInTooltip: Boolean = true, showInGui: Boolean = true, orderIndex: Int = 0): StackPropertyBool {
        return StackPropertyBool(name, defaultValue, showInTooltip, showInGui, orderIndex, getSynchronizerBool())
    }

    fun makeBlockStateProperty(name: String, defaultValue: IBlockState, showInTooltip: Boolean = false, showInGui: Boolean = true, orderIndex: Int = 0): StackPropertyIBlockState {
        return StackPropertyIBlockState(name, defaultValue, showInTooltip, showInGui, orderIndex, getSynchronizerIBlockState())
    }

    fun makeBlockPosProperty(name: String, defaultValue: BlockPos, showInTooltip: Boolean = false, showInGui: Boolean = true, orderIndex: Int = 0): StackPropertyBlockPos {
        return StackPropertyBlockPos(name, defaultValue, showInTooltip, showInGui, orderIndex, getSynchronizerBlockPos())
    }

    fun makeEnumFacingProperty(name: String, defaultValue: EnumFacing, showInTooltip: Boolean = false, showInGui: Boolean = true, orderIndex: Int = 0): StackPropertyEnumFacing {
        return StackPropertyEnumFacing(name, defaultValue, showInTooltip, showInGui, orderIndex, getSynchronizerEnumFacing())
    }

    fun makeUUIDProperty(name: String, defaultValue: UUID, showInTooltip: Boolean = false, showInGui: Boolean = true, orderIndex: Int = 0): StackPropertyUUID {
        return StackPropertyUUID(name, defaultValue, showInTooltip, showInGui, orderIndex, getSynchronizerUUID())
    }

    protected open fun getSynchronizerInt() = { itemId: Int, property: StackProperty<Int>, value: Int ->
        MessageSyncPropertyInt(itemId, property.name, value).send()
    }

    protected open fun getSynchronizerFloat() = { itemId: Int, property: StackProperty<Float>, value: Float ->
        MessageSyncPropertyFloat(itemId, property.name, value).send()
    }

    protected open fun getSynchronizerBool() = { itemId: Int, property: StackProperty<Boolean>, value: Boolean ->
        MessageSyncPropertyBool(itemId, property.name, value).send()
    }

    protected open fun getSynchronizerEnumFacing() = { itemId: Int, property: StackProperty<EnumFacing>, value: EnumFacing ->
        MessageSyncPropertyEnumFacing(itemId, property.name, value).send()
    }

    protected open fun getSynchronizerBlockPos() = { itemId: Int, property: StackProperty<BlockPos>, value: BlockPos ->
        MessageSyncPropertyBlockPos(itemId, property.name, value).send()
    }

    protected open fun getSynchronizerIBlockState() = { itemId: Int, property: StackProperty<IBlockState>, value: IBlockState ->
        MessageSyncPropertyIBlockState(itemId, property.name, value).send()
    }

    protected open fun getSynchronizerUUID() = { itemId: Int, property: StackProperty<UUID>, value: UUID ->
        MessageSyncPropertyUUID(itemId, property.name, value).send()
    }
}