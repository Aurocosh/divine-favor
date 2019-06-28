package aurocosh.divinefavor.common.stack_properties.properties

import aurocosh.divinefavor.common.lib.extensions.getBlockState
import aurocosh.divinefavor.common.lib.extensions.setBlockState
import net.minecraft.block.state.IBlockState
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class StackPropertyIBlockState(name: String, defaultValue: IBlockState, showInTooltip: Boolean, showInGui: Boolean, orderIndex: Int, serverSync: (Int, StackProperty<IBlockState>, IBlockState) -> Unit) : StackProperty<IBlockState>(name, defaultValue, showInTooltip, showInGui, orderIndex, serverSync) {
    override fun getValueFromTag(compound: NBTTagCompound): IBlockState {
        return compound.getBlockState(tag)
    }

    override fun setValueToTag(compound: NBTTagCompound, value: IBlockState) {
        compound.setBlockState(tag, value)
    }

    @SideOnly(Side.CLIENT)
    override fun toLocalString(stack: ItemStack): String {
        val value = getValue(stack)
        return I18n.format(displayKey, value.block.localizedName)
    }
}
