package aurocosh.divinefavor.common.stack_properties.properties

import aurocosh.divinefavor.common.constants.ConstLang
import aurocosh.divinefavor.common.stack_properties.properties.base.StackProperty
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class StackPropertyBool(name: String, defaultValue: Boolean, showInTooltip: Boolean, showInGui: Boolean, orderIndex: Int, val onKey: String = ConstLang.onKey, val offKey: String = ConstLang.offKey) : StackProperty<Boolean>(name, defaultValue, showInTooltip, showInGui, orderIndex) {
    override fun getValueFromTag(compound: NBTTagCompound): Boolean {
        return compound.getBoolean(tag)
    }

    override fun setValueToTag(compound: NBTTagCompound, value: Boolean) {
        compound.setBoolean(tag, value)
    }

    @SideOnly(Side.CLIENT)
    override fun toLocalString(stack: ItemStack): String {
        val value = getValue(stack)
        val valueKey = if (value) onKey else offKey
        val valueString = I18n.format(valueKey)
        return I18n.format(displayKey, valueString)
    }
}
