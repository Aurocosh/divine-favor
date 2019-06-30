package aurocosh.divinefavor.common.stack_properties.properties

import aurocosh.divinefavor.common.lib.IIndexedEnum
import aurocosh.divinefavor.common.stack_properties.properties.base.StackProperty
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class StackPropertyEnum<T>(name: String, defaultValue: T, private val converter: IIndexedEnum<T>, showInTooltip: Boolean, showInGui: Boolean, orderIndex: Int) : StackProperty<T>(name, defaultValue, showInTooltip, showInGui, orderIndex) where T : Enum<T> {

    fun getMaxOrdinal() = converter.getMaxOrdinal()

    override fun getValueFromTag(compound: NBTTagCompound): T {
        val ordinal = compound.getInteger(tag)
        return converter[ordinal]
    }

    override fun setValueToTag(compound: NBTTagCompound, value: T) {
        compound.setInteger(tag, value.ordinal)
    }

    override fun unsafeValueSet(stack: ItemStack, value: Any, sync: Boolean): Boolean {
        return if (value is Int) setOrdinalValue(stack, value, sync) else super.unsafeValueSet(stack, value, sync)
    }

    fun setOrdinalValue(stack: ItemStack, value: Int, syncBoolean: Boolean = false): Boolean {
        val enumValue = converter[value]
        return setValue(stack, enumValue, syncBoolean)
    }

    @SideOnly(Side.CLIENT)
    override fun toLocalString(stack: ItemStack): String {
        val value = getValue(stack)
        val formattedValue = I18n.format("${displayKey}_value_$value")
        return I18n.format(displayKey, formattedValue)
    }

    @SideOnly(Side.CLIENT)
    fun toLocalString(value: Int): String {
        val enum = converter[value].toString().toLowerCase()
        val formattedValue = I18n.format("${displayKey}_value_$enum")
        return I18n.format(displayKey, formattedValue)
    }
}
