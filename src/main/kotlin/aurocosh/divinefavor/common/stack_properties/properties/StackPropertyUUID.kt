package aurocosh.divinefavor.common.stack_properties.properties

import aurocosh.divinefavor.common.stack_properties.properties.base.StackProperty
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class StackPropertyUUID(name: String, defaultValue: UUID, showInTooltip: Boolean, showInGui: Boolean, orderIndex: Int) : StackProperty<UUID>(name, defaultValue, showInTooltip, showInGui, orderIndex) {
    override fun getValueFromTag(compound: NBTTagCompound): UUID {
        val propertyTag = compound.getCompoundTag(tag)
        return propertyTag.getUniqueId("") ?: defaultValue
    }

    override fun setValueToTag(compound: NBTTagCompound, value: UUID) {
        val propertyTag = NBTTagCompound()
        propertyTag.setUniqueId("",value)
        compound.setTag(tag, propertyTag)
    }

    @SideOnly(Side.CLIENT)
    override fun toLocalString(stack: ItemStack): String {
        return I18n.format(displayKey)
    }
}
