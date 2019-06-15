package aurocosh.divinefavor.common.item.talisman.properties

import aurocosh.divinefavor.common.constants.ConstLang
import aurocosh.divinefavor.common.lib.extensions.compound
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class StackPropertyBool(name: String, defaultValue: Boolean) : StackProperty<Boolean>(name, defaultValue) {
    override fun getValueImpl(stack: ItemStack): Boolean {
        return stack.compound.getBoolean(tag)
    }

    override fun setValueImpl(stack: ItemStack, value: Boolean) {
        stack.compound.setBoolean(tag, value)
    }

    override fun next(stack: ItemStack): Boolean {
        return !getValue(stack)
    }

    override fun previous(stack: ItemStack): Boolean {
        return !getValue(stack)
    }

    @SideOnly(Side.CLIENT)
    override fun toLocalString(stack: ItemStack): String {
        val value = getValue(stack)
        val valueKey = if (value) ConstLang.yesKey else ConstLang.noKey
        val valueString = I18n.format(valueKey)
        return I18n.format(displayKey, valueString)
    }
}
