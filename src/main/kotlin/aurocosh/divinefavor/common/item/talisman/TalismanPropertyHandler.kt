package aurocosh.divinefavor.common.item.talisman

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.talisman_properties.*
import net.minecraft.block.state.IBlockState
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class TalismanPropertyHandler(private val parentName: String) : IPropertyAccessor {
    private val propertyList = ArrayList<TalismanProperty<out Any>>()
    private val propertyMap = HashMap<String, TalismanProperty<out Any>>()

    override val list: List<TalismanProperty<out Any>>
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

    fun <T : TalismanProperty<out Any>> registerProperty(property: T): T {
        if (propertyMap.containsKey(property.name)) {
            DivineFavor.logger.error("Talisman property conflict in $parentName. Conflicting property name ${property.name}")
        } else {
            propertyList.add(property)
            propertyMap[property.name] = property
        }
        return property
    }

    fun registerIntProperty(name: String, defaultValue: Int, minValue: Int = 1, maxValue: Int = defaultValue, onPropertyChanged: (ItemStack) -> Unit = {}): TalismanPropertyInt {
        val property = TalismanPropertyInt(name, defaultValue, minValue, maxValue, onPropertyChanged)
        registerProperty(property)
        return property
    }

    fun registerBoolProperty(name: String, defaultValue: Boolean, onPropertyChanged: (ItemStack) -> Unit = {}): TalismanPropertyBool {
        val property = TalismanPropertyBool(name, defaultValue, onPropertyChanged)
        registerProperty(property)
        return property
    }

    fun registerBlockStateProperty(name: String, defaultValue: IBlockState, onPropertyChanged: (ItemStack) -> Unit = {}): TalismanPropertyIBlockState {
        val property = TalismanPropertyIBlockState(name, defaultValue, onPropertyChanged)
        registerProperty(property)
        return property
    }

    fun registerBlockPosProperty(name: String, defaultValue: BlockPos, onPropertyChanged: (ItemStack) -> Unit = {}): TalismanPropertyBlockPos {
        val property = TalismanPropertyBlockPos(name, defaultValue, onPropertyChanged)
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
        propertyList.map { it.toLocalString(stack) }.forEach { tooltip.add(it) }
        return tooltip
    }

    companion object {
        private const val TAG_PROPERTY_INDEX = "PropertyIndex"
    }
}