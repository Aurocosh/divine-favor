package aurocosh.divinefavor.common.receipes.dynamic

import aurocosh.divinefavor.common.item.gems.base.IGemWithMask
import aurocosh.divinefavor.common.item.gems.properties.GemMaskProperties
import aurocosh.divinefavor.common.lib.extensions.*
import aurocosh.divinefavor.common.receipes.base.ModRecipe
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.world.World

class GemMaskRecipe : ModRecipe("gem_mask_recipe") {
    override fun canFit(width: Int, height: Int): Boolean {
        return width * height >= 2
    }

    override fun getRecipeOutput(): ItemStack {
        return ItemStack.EMPTY
    }

    override fun getCraftingResult(inv: InventoryCrafting): ItemStack {
        val stacks = inv.asSequence().filter(ItemStack::isNotEmpty).toList()
        val stacksToMask = stacks.first { it.item is IGemWithMask }
        val stackUsedAsMask = stacks.first { it.item !is IGemWithMask }

        val maskId = stackUsedAsMask.item.regName.toString()
        val maskMeta = stackUsedAsMask.metadata

        val result = stacksToMask.copy()
        result.set(GemMaskProperties.maskItemId, maskId)
        result.set(GemMaskProperties.maskItemMeta, maskMeta)

        return result
    }

    override fun matches(inv: InventoryCrafting, worldIn: World): Boolean {
        val stacks = inv.asSequence().filter(ItemStack::isNotEmpty).toList()
        if (stacks.size != 2)
            return false

        val stacksWithMask = stacks.filter { it.item is IGemWithMask }
        return stacksWithMask.size == 1
    }

    override fun getRemainingItems(inv: InventoryCrafting): NonNullList<ItemStack> {
        val stacks = NonNullList.withSize(inv.sizeInventory, ItemStack.EMPTY)
        for (index in inv.indices) {
            val stack = inv.getStackInSlot(index)
            if (!stack.isEmpty && stack.item !is IGemWithMask){
                val remainingStack = stack.copy()
                remainingStack.count = 1
                stacks[index] = remainingStack
            }
        }
        return stacks
    }
}