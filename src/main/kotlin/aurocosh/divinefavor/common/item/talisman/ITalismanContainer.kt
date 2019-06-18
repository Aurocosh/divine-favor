package aurocosh.divinefavor.common.item.talisman

import net.minecraft.item.ItemStack

interface ITalismanContainer {
    fun getTalismanStack(stack: ItemStack): ItemStack
}