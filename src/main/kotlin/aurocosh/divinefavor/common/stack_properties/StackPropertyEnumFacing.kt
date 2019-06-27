package aurocosh.divinefavor.common.stack_properties

import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class StackPropertyEnumFacing(name: String, defaultValue: EnumFacing, showInTooltip: Boolean, showInGui: Boolean, orderIndex: Int, serverSync: (Int, StackProperty<EnumFacing>, EnumFacing) -> Unit) : StackProperty<EnumFacing>(name, defaultValue, showInTooltip, showInGui, orderIndex, serverSync) {
    override fun getValueFromTag(compound: NBTTagCompound): EnumFacing {
        return EnumFacing.byIndex(compound.getInteger(tag))
    }

    override fun setValueToTag(compound: NBTTagCompound, value: EnumFacing) {
        compound.setInteger(tag, value.ordinal)
    }

    @SideOnly(Side.CLIENT)
    override fun toLocalString(stack: ItemStack): String {
        val value = getValue(stack)
        return I18n.format(displayKey, value.name)
    }
}
