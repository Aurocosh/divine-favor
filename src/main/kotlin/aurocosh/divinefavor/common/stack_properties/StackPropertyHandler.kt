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
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

open class StackPropertyHandler(private val parentName: String, private val propertyGenerator: StackPropertyGenerator = StackPropertyGenerator()) : IPropertyAccessor {
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

    fun registerIntProperty(name: String, defaultValue: Int, minValue: Int = 1, maxValue: Int = defaultValue, showInTooltip: Boolean = true, showInGui: Boolean = true, orderIndex: Int = 0) =
            registerProperty(propertyGenerator.makeIntProperty(name, defaultValue, minValue, maxValue, showInTooltip, showInGui, orderIndex))

    fun registerFloatProperty(name: String, defaultValue: Float, minValue: Float = 1f, maxValue: Float = defaultValue, showInTooltip: Boolean = true, showInGui: Boolean = true, orderIndex: Int = 0) =
            registerProperty(propertyGenerator.makeFloatProperty(name, defaultValue, minValue, maxValue, showInTooltip, showInGui, orderIndex))

    fun registerBoolProperty(name: String, defaultValue: Boolean, showInTooltip: Boolean = true, showInGui: Boolean = true, orderIndex: Int = 0) =
            registerProperty(propertyGenerator.makeBoolProperty(name, defaultValue, showInTooltip, showInGui, orderIndex))

    fun registerBlockStateProperty(name: String, defaultValue: IBlockState, showInTooltip: Boolean = false, showInGui: Boolean = true, orderIndex: Int = 0) =
            registerProperty(propertyGenerator.makeBlockStateProperty(name, defaultValue, showInTooltip, showInGui, orderIndex))

    fun registerBlockPosProperty(name: String, defaultValue: BlockPos, showInTooltip: Boolean = false, showInGui: Boolean = true, orderIndex: Int = 0) =
            registerProperty(propertyGenerator.makeBlockPosProperty(name, defaultValue, showInTooltip, showInGui, orderIndex))

    fun registerEnumFacingProperty(name: String, defaultValue: EnumFacing, showInTooltip: Boolean = false, showInGui: Boolean = true, orderIndex: Int = 0) =
            registerProperty(propertyGenerator.makeEnumFacingProperty(name, defaultValue, showInTooltip, showInGui, orderIndex))

    fun registerUUIDProperty(name: String, defaultValue: UUID, showInTooltip: Boolean = false, showInGui: Boolean = true, orderIndex: Int = 0) =
            registerProperty(propertyGenerator.makeUUIDProperty(name, defaultValue, showInTooltip, showInGui, orderIndex))

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