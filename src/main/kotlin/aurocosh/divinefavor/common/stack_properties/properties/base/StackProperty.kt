package aurocosh.divinefavor.common.stack_properties.properties.base

import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.lib.extensions.checkForTag
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.lib.extensions.isPropertySet
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

abstract class StackProperty<T : Any>(val name: String, val defaultValue: T, val showInTooltip: Boolean, val showInGui: Boolean, val orderIndex: Int) {
    val tag = "tag_$name"
    val tooltipKey = ResourceNamer.getTypedNameString("tooltip", "property", name)
    val displayKey = ResourceNamer.getTypedNameString("name", "property", name)

    private val changeListeners: MutableSet<(ItemStack, T) -> Unit> = HashSet()
    private val syncListeners: MutableSet<(Int, StackProperty<T>, T) -> Unit> = HashSet()

    fun addChangeListener(listener: (ItemStack, T) -> Unit) = changeListeners.add(listener)
    fun addSyncListener(listener: (Int, StackProperty<T>, T) -> Unit) = syncListeners.add(listener)

    fun getValue(stack: ItemStack): T {
        if (!stack.checkForTag(tag))
            return defaultValue
        val compound = stack.tagCompound as NBTTagCompound
        return getValueFromTag(compound)
    }

    fun setValue(stack: ItemStack, value: T, sync: Boolean = false): Boolean {
        val current = getValue(stack)
        if (current == value)
            return false
        setValueToTag(stack.compound, value)
        changeListeners.forEach { it.invoke(stack, value) }
        if (sync) {
            val itemId = Item.getIdFromItem(stack.item)
            syncListeners.forEach { it.invoke(itemId, this, value) }
        }
        return true
    }

    fun copy(fromStack: ItemStack, toStack: ItemStack, sync: Boolean = false) {
        if (fromStack.isPropertySet(this))
            setValue(toStack, getValue(fromStack), sync)
    }

    abstract fun getValueFromTag(compound: NBTTagCompound): T
    abstract fun setValueToTag(compound: NBTTagCompound, value: T)

    @SideOnly(Side.CLIENT)
    abstract fun toLocalString(stack: ItemStack): String
}