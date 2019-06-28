package aurocosh.divinefavor.common.stack_properties.properties

import aurocosh.divinefavor.common.lib.IIndexedEnum
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class StackPropertyEnum<T>(name: String, defaultValue: T, private val converter: IIndexedEnum<T>, showInTooltip: Boolean, showInGui: Boolean, orderIndex: Int, serverSync: (Int, StackProperty<T>, T) -> Unit) : StackProperty<T>(name, defaultValue, showInTooltip, showInGui, orderIndex, serverSync) where T : Enum<T> {

    fun getMaxOrdinal() = converter.getMaxOrdinal()

    override fun getValueFromTag(compound: NBTTagCompound): T {
        val ordinal = compound.getInteger(tag)
        return converter[ordinal]
    }

    override fun setValueToTag(compound: NBTTagCompound, value: T) {
        compound.setInteger(tag, value.ordinal)
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
