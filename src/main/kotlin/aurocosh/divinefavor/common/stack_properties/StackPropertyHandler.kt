package aurocosh.divinefavor.common.stack_properties

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.compound
import net.minecraft.block.state.IBlockState
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

abstract class StackPropertyHandler(private val parentName: String) : IPropertyAccessor {
    private val propertyList = ArrayList<StackProperty<out Any>>()
    private val propertyMap = HashMap<String, StackProperty<out Any>>()

    override val list: List<StackProperty<out Any>>
        get() = propertyList

    override fun get(index: Int) = propertyList[index]
    override fun get(name: String) = propertyMap[name]

    override fun exist(index: Int): Boolean = (index > 0 && index < propertyList.size)
    override fun exist(name: String) = propertyMap.containsKey(name)

    override fun getSelectedIndex(stack: ItemStack): Int {
        if (propertyList.isEmpty())
            return -1;
        if (!stack.hasTagCompound())
            return 0
        return stack.compound.getInteger(TAG_PROPERTY_INDEX)
    }

    override fun setSelectedIndex(stack: ItemStack, index: Int) {
        if (propertyList.isEmpty())
            return
        stack.compound.setInteger(TAG_PROPERTY_INDEX, index)
    }

    fun <T : StackProperty<out Any>> registerProperty(property: T): T {
        if (propertyMap.containsKey(property.name)) {
            DivineFavor.logger.error("Talisman property conflict in $parentName. Conflicting property name ${property.name}")
        } else {
            propertyMap[property.name] = property
            propertyList.add(property)
            propertyList.sortBy { it.orderIndex }
        }
        return property
    }

    fun registerIntProperty(name: String, defaultValue: Int, minValue: Int = 1, maxValue: Int = defaultValue, showInTooltip: Boolean = true, orderIndex: Int = 0): StackPropertyInt {
        val property = StackPropertyInt(name, defaultValue, minValue, maxValue, showInTooltip, orderIndex, getSynchronizerInt())
        registerProperty(property)
        return property
    }

    fun registerBoolProperty(name: String, defaultValue: Boolean, showInTooltip: Boolean = true, orderIndex: Int = 0): StackPropertyBool {
        val property = StackPropertyBool(name, defaultValue, showInTooltip, orderIndex, getSynchronizerBool())
        registerProperty(property)
        return property
    }

    fun registerBlockStateProperty(name: String, defaultValue: IBlockState, showInTooltip: Boolean = false, orderIndex: Int = 0): StackPropertyIBlockState {
        val property = StackPropertyIBlockState(name, defaultValue, showInTooltip, orderIndex, getSynchronizerIBlockState())
        registerProperty(property)
        return property
    }

    fun registerBlockPosProperty(name: String, defaultValue: BlockPos, showInTooltip: Boolean = false, orderIndex: Int = 0): StackPropertyBlockPos {
        val property = StackPropertyBlockPos(name, defaultValue, showInTooltip, orderIndex, getSynchronizerBlockPos())
        registerProperty(property)
        return property
    }

    fun registerEnumFacingProperty(name: String, defaultValue: EnumFacing, showInTooltip: Boolean = false, orderIndex: Int = 0): StackPropertyEnumFacing {
        val property = StackPropertyEnumFacing(name, defaultValue, showInTooltip, orderIndex, getSynchronizerEnumFacing())
        registerProperty(property)
        return property
    }

    @SideOnly(Side.CLIENT)
    override fun getPropertyTooltip(stack: ItemStack): List<String> {
        if (propertyList.isEmpty())
            return emptyList()

        val tooltip = ArrayList<String>()
        tooltip.add("")
        tooltip.add(I18n.format("tooltip.divinefavor:property_list"))
        propertyList.S.filter { it.showInTooltip }.map { it.toLocalString(stack) }.forEach { tooltip.add(it) }
        return tooltip
    }

    abstract fun getSynchronizerInt(): (StackProperty<Int>, Int) -> Unit
    abstract fun getSynchronizerBool(): (StackProperty<Boolean>, Boolean) -> Unit
    abstract fun getSynchronizerEnumFacing(): (StackProperty<EnumFacing>, EnumFacing) -> Unit
    abstract fun getSynchronizerBlockPos(): (StackProperty<BlockPos>, BlockPos) -> Unit
    abstract fun getSynchronizerIBlockState(): (StackProperty<IBlockState>, IBlockState) -> Unit

    companion object {
        private const val TAG_PROPERTY_INDEX = "PropertyIndex"
    }
}