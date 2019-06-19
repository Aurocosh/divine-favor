package aurocosh.divinefavor.common.item.talisman

import net.minecraft.item.ItemStack

interface ITalismanStackContainer {
    fun getTalismanStack(stack: ItemStack): ItemStack
}