package aurocosh.divinefavor.common.talisman_properties

import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.lib.extensions.getBlockPos
import aurocosh.divinefavor.common.lib.extensions.setBlockPos
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanPropertyBlockPos
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class TalismanPropertyBlockPos(name: String, defaultValue: BlockPos, onPropertyChanged: (ItemStack) -> Unit) : TalismanProperty<BlockPos>(name, defaultValue, onPropertyChanged) {
    override fun getValueImpl(stack: ItemStack): BlockPos {
        return stack.compound.getBlockPos(tag)
    }

    override fun setValueImpl(stack: ItemStack, value: BlockPos) {
        stack.compound.setBlockPos(tag, value)
    }

    override fun syncToServer(value: BlockPos) {
        MessageSyncTalismanPropertyBlockPos(name, value).send()
    }

    @SideOnly(Side.CLIENT)
    override fun toLocalString(stack: ItemStack): String {
        val value = getValue(stack)
        return I18n.format(displayKey, value.toString())
    }
}
