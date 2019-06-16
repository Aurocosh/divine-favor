package aurocosh.divinefavor.common.item.talisman

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.stack_properties.*
import net.minecraft.block.state.IBlockState
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class TalismanPropertyHandler(private val parentName: String) : IPropertyAccessor {
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
            propertyList.add(property)
            propertyMap[property.name] = property
        }
        return property
    }

    fun registerIntProperty(name: String, defaultValue: Int, minValue: Int = 1, maxValue: Int = defaultValue, showInTooltip: Boolean = true): StackPropertyInt {
        val property = StackPropertyInt(name, defaultValue, minValue, maxValue, showInTooltip, TalismanPropertySynchronizer::syncInt)
        registerProperty(property)
        return property
    }

    fun registerBoolProperty(name: String, defaultValue: Boolean, showInTooltip: Boolean = true): StackPropertyBool {
        val property = StackPropertyBool(name, defaultValue, showInTooltip, TalismanPropertySynchronizer::syncBool)
        registerProperty(property)
        return property
    }

    fun registerBlockStateProperty(name: String, defaultValue: IBlockState, showInTooltip: Boolean = false): StackPropertyIBlockState {
        val property = StackPropertyIBlockState(name, defaultValue, showInTooltip, TalismanPropertySynchronizer::syncIBlockState)
        registerProperty(property)
        return property
    }

    fun registerBlockPosProperty(name: String, defaultValue: BlockPos, showInTooltip: Boolean = false): StackPropertyBlockPos {
        val property = StackPropertyBlockPos(name, defaultValue, showInTooltip, TalismanPropertySynchronizer::syncBlockPos)
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

    companion object {
        private const val TAG_PROPERTY_INDEX = "PropertyIndex"
    }
}