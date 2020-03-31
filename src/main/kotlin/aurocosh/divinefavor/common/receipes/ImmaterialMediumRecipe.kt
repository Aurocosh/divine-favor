package aurocosh.divinefavor.common.receipes

import aurocosh.divinefavor.common.item.gems.ItemCallingStone
import aurocosh.divinefavor.common.lib.EmptyConst.emptyLocation
import aurocosh.divinefavor.common.lib.extensions.S
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.ResourceLocation

class ImmaterialMediumRecipe(val result: ItemStack, val callingStones: List<Ingredient>, val ingredients: List<Ingredient>) {
    val name: ResourceLocation
        get() = result.item.registryName ?: emptyLocation()

    private val acceptableStones = callingStones.map { it.getMatchingStacks().first().item }.toSet()
    private val possibleItemIds : Set<Int> by lazy { ingredients.flatMap { it.getMatchingStacks().asIterable() }.map { Item.getIdFromItem(it.item) }.toSet() }

    fun isMatching(callingStone: ItemCallingStone, stacks: List<ItemStack>): Boolean {
        if (stacks.size != ingredients.size)
            return false
        if (!acceptableStones.contains(callingStone))
            return false
        val itemIdSet = stacks.map { Item.getIdFromItem(it.item) }.toSet()
        if (!possibleItemIds.containsAll(itemIdSet))
            return false

        val requiredIngredients = ingredients.toMutableList()
        for (stack in stacks) {
            val match = requiredIngredients.firstOrNull { it.apply(stack) }
            if (match != null)
                requiredIngredients.remove(match)
        }
        return requiredIngredients.isEmpty()
    }

    fun getStringId(): String {
        return ingredients.S
                .map { it.getMatchingStacks() }.toList()
                .map { it.joinToString("_") { stack -> Item.getIdFromItem(stack.item).toString() + "_" + stack.metadata } }.toList()
                .joinToString(",")
    }
}