package aurocosh.divinefavor.common.stack_properties

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.IIndexedEnum
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.stack_properties.generators.IPropertySynchronizer
import aurocosh.divinefavor.common.stack_properties.generators.StackPropertySynchronizer
import aurocosh.divinefavor.common.stack_properties.interfaces.IPropertyAccessor
import aurocosh.divinefavor.common.stack_properties.properties.*
import aurocosh.divinefavor.common.stack_properties.properties.base.StackProperty
import net.minecraft.block.state.IBlockState
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

open class StackPropertyHandler(private val parentName: String, private val synchronizer: IPropertySynchronizer = StackPropertySynchronizer) : IPropertyAccessor {
    private val propertyList = ArrayList<StackProperty<out Any>>()
    private val propertyMap = HashMap<String, StackProperty<out Any>>()

    override val list get() = propertyList

    override fun get(index: Int) = propertyList[index]
    override operator fun get(name: String) = propertyMap[name]

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

    fun <T : Any, K : StackProperty<T>> registerProperty(property: K): K {
        if (propertyMap.containsKey(property.name)) {
            DivineFavor.logger.error("Talisman property conflict in $parentName. Conflicting property name ${property.name}")
        } else {
            propertyMap[property.name] = property
            propertyList.add(property)
            propertyList.sortBy { it.orderIndex }
            property.addSyncListener(synchronizer.getSynchronizer(property))
        }
        return property
    }

    fun registerIntProperty(name: String, defaultValue: Int, minValue: Int = 1, maxValue: Int = defaultValue, showInTooltip: Boolean = true, showInGui: Boolean = true, orderIndex: Int = 0) =
            registerProperty(StackPropertyInt(name, defaultValue, minValue, maxValue, showInTooltip, showInGui, orderIndex))

    fun registerFloatProperty(name: String, defaultValue: Float, minValue: Float = 1f, maxValue: Float = defaultValue, showInTooltip: Boolean = true, showInGui: Boolean = true, orderIndex: Int = 0) =
            registerProperty(StackPropertyFloat(name, defaultValue, minValue, maxValue, showInTooltip, showInGui, orderIndex))

    fun registerBoolProperty(name: String, defaultValue: Boolean, showInTooltip: Boolean = true, showInGui: Boolean = true, orderIndex: Int = 0) =
            registerProperty(StackPropertyBool(name, defaultValue, showInTooltip, showInGui, orderIndex))

    fun registerBlockStateProperty(name: String, defaultValue: IBlockState, showInTooltip: Boolean = false, showInGui: Boolean = true, orderIndex: Int = 0) =
            registerProperty(StackPropertyIBlockState(name, defaultValue, showInTooltip, showInGui, orderIndex))

    fun registerBlockPosProperty(name: String, defaultValue: BlockPos, showInTooltip: Boolean = false, showInGui: Boolean = true, orderIndex: Int = 0) =
            registerProperty(StackPropertyBlockPos(name, defaultValue, showInTooltip, showInGui, orderIndex))

    fun registerEnumFacingProperty(name: String, defaultValue: EnumFacing, showInTooltip: Boolean = false, showInGui: Boolean = true, orderIndex: Int = 0) =
            registerProperty(StackPropertyEnumFacing(name, defaultValue, showInTooltip, showInGui, orderIndex))

    fun registerUUIDProperty(name: String, defaultValue: UUID, showInTooltip: Boolean = false, showInGui: Boolean = true, orderIndex: Int = 0) =
            registerProperty(StackPropertyUUID(name, defaultValue, showInTooltip, showInGui, orderIndex))

    fun registerStringProperty(name: String, defaultValue: String, showInTooltip: Boolean = false, showInGui: Boolean = true, orderIndex: Int = 0) =
            registerProperty(StackPropertyString(name, defaultValue, showInTooltip, showInGui, orderIndex))

    fun <T> registerEnumProperty(name: String, defaultValue: T, converter: IIndexedEnum<T>, showInTooltip: Boolean = false, showInGui: Boolean = true, orderIndex: Int = 0) where T : Enum<T> =
            registerProperty(StackPropertyEnum(name, defaultValue, converter, showInTooltip, showInGui, orderIndex))

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

    companion object {
        private const val TAG_PROPERTY_INDEX = "PropertyIndex"
    }
}