package aurocosh.divinefavor.common.receipes.dynamic

import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.item.gems.ItemWarpMarker
import aurocosh.divinefavor.common.lib.extensions.asSequence
import aurocosh.divinefavor.common.lib.extensions.isNotEmpty
import aurocosh.divinefavor.common.receipes.base.ModRecipe
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.world.World

class GemStabilizerRecipe : ModRecipe("gem_stabilizer_recipe") {
    override fun canFit(width: Int, height: Int): Boolean {
        return width * height >= 2
    }

    override fun getRecipeOutput(): ItemStack {
        return ItemStack.EMPTY
    }

    override fun getCraftingResult(inv: InventoryCrafting): ItemStack {
        val gem = inv.asSequence().filter(ItemStack::isNotEmpty).first { it.item == ModItems.warp_gem }
        val result = ItemStack(ModItems.stable_warp_gem)
        ItemWarpMarker.propertyHandler.copy(gem, result)
        return result
    }

    override fun matches(inv: InventoryCrafting, worldIn: World): Boolean {
        var gemCount = 0
        var otherCount = 0
        var stabilizerCount = 0

        val stacks = inv.asSequence().filter(ItemStack::isNotEmpty)
        for (stack in stacks) {
            when (stack.item) {
                ModItems.gem_stabilizer -> stabilizerCount++
                ModItems.warp_gem -> gemCount++
                else -> otherCount++
            }
        }

        return stabilizerCount == 1 && gemCount == 1 && otherCount == 0
    }

    override fun getRemainingItems(inv: InventoryCrafting): NonNullList<ItemStack> {
        return NonNullList.withSize(inv.sizeInventory, ItemStack.EMPTY)
    }
}