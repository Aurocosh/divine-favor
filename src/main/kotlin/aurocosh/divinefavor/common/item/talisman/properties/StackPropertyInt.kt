package aurocosh.divinefavor.common.item.talisman.properties

import aurocosh.divinefavor.common.lib.extensions.compound
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.util.math.MathHelper
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class StackPropertyInt(name: String, defaultValue: Int, val minValue: Int, val maxValue: Int) : StackProperty<Int>(name, defaultValue) {

    override fun getValueImpl(stack: ItemStack): Int {
        return stack.compound.getInteger(tag)
    }

    override fun setValueImpl(stack: ItemStack, value: Int) {
        stack.compound.setInteger(tag, MathHelper.clamp(value, minValue, maxValue))
    }

    override fun next(stack: ItemStack): Int {
        return getValue(stack) + 1
    }

    override fun previous(stack: ItemStack): Int {
        return getValue(stack) - 1
    }

    @SideOnly(Side.CLIENT)
    override fun toLocalString(stack: ItemStack): String {
        val value = getValue(stack)
        return I18n.format(displayKey, value)
    }
}
