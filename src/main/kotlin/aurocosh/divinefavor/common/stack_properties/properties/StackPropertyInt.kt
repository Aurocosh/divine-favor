package aurocosh.divinefavor.common.stack_properties.properties

import aurocosh.divinefavor.common.stack_properties.properties.base.StackProperty
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.MathHelper
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class StackPropertyInt(name: String, defaultValue: Int, val minValue: Int, val maxValue: Int, showInTooltip: Boolean, showInGui: Boolean, orderIndex: Int) : StackProperty<Int>(name, defaultValue, showInTooltip, showInGui, orderIndex) {
    override fun getValueFromTag(compound: NBTTagCompound): Int {
        return compound.getInteger(tag)
    }

    override fun setValueToTag(compound: NBTTagCompound, value: Int) {
        compound.setInteger(tag, MathHelper.clamp(value, minValue, maxValue))
    }

    @SideOnly(Side.CLIENT)
    override fun toLocalString(stack: ItemStack): String {
        val value = getValue(stack)
        return I18n.format(displayKey, value)
    }
}
