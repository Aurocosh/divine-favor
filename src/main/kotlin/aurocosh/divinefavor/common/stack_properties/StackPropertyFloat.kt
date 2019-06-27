package aurocosh.divinefavor.common.stack_properties

import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.MathHelper
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class StackPropertyFloat(name: String, defaultValue: Float, val minValue: Float, val maxValue: Float, showInTooltip: Boolean, showInGui: Boolean, orderIndex: Int, serverSync: (Int, StackProperty<Float>, Float) -> Unit) : StackProperty<Float>(name, defaultValue, showInTooltip, showInGui, orderIndex, serverSync) {
    override fun getValueFromTag(compound: NBTTagCompound): Float {
        return compound.getFloat(tag)
    }

    override fun setValueToTag(compound: NBTTagCompound, value: Float) {
        compound.setFloat(tag, MathHelper.clamp(value, minValue, maxValue))
    }

    @SideOnly(Side.CLIENT)
    override fun toLocalString(stack: ItemStack): String {
        val value = getValue(stack)
        return I18n.format(displayKey, value)
    }
}
