package aurocosh.divinefavor.common.stack_properties.properties

import aurocosh.divinefavor.common.lib.extensions.getBlockPos
import aurocosh.divinefavor.common.lib.extensions.setBlockPos
import aurocosh.divinefavor.common.stack_properties.properties.base.StackProperty
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class StackPropertyBlockPos(name: String, defaultValue: BlockPos, showInTooltip: Boolean, showInGui: Boolean, orderIndex: Int) : StackProperty<BlockPos>(name, defaultValue, showInTooltip, showInGui, orderIndex) {
    override fun getValueFromTag(compound: NBTTagCompound): BlockPos {
        return compound.getBlockPos(tag)
    }

    override fun setValueToTag(compound: NBTTagCompound, value: BlockPos) {
        compound.setBlockPos(tag, value)
    }

    @SideOnly(Side.CLIENT)
    override fun toLocalString(stack: ItemStack): String {
        val value = getValue(stack)
        return I18n.format(displayKey, value.toString())
    }
}
