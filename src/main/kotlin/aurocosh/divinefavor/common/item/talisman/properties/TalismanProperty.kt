package aurocosh.divinefavor.common.item.talisman.properties

import aurocosh.divinefavor.common.core.ResourceNamer
import net.minecraft.item.ItemStack

abstract class TalismanProperty<T>(val name: String, val prefix: String, val suffix: String, val defaultValue: T) {
    val tag = "Property$name"
    val tooltipKey = ResourceNamer.getNameString("tooltip", name)

    abstract fun getValue(stack: ItemStack): T
    abstract fun setValue(stack: ItemStack, value: T)

    abstract fun setValueAndSync(stack: ItemStack, value: T, playerSlot: Int)
    abstract fun syncToServer(playerSlot: Int, value: T)
}