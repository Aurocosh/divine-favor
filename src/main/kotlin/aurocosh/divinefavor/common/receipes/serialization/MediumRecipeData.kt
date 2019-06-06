package aurocosh.divinefavor.common.receipes.serialization

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.calling_stones.ItemCallingStone
import aurocosh.divinefavor.common.receipes.ImmaterialMediumRecipe
import aurocosh.divinefavor.common.receipes.RecipeBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import java.util.*

class MediumRecipeData {
    var result: RecipeResult = RecipeResult()
    var stones: List<String> = emptyList()
    var ingredients: List<RecipeIngredient> = emptyList()

    fun toRecipes(): List<ImmaterialMediumRecipe> {
        val resultStack = result.toItemStack()
        if (resultStack.isEmpty) {
            DivineFavor.logger.error("Recipe error: ${result.item}. Result is invalid: ${result.item}")
            return emptyList()
        }
        val callingStones = ArrayList<ItemCallingStone>()
        for (stoneName in stones) {
            val stoneItem = getCallingStone(stoneName)
            if (stoneItem != null)
                callingStones.add(stoneItem)
        }

        if (callingStones.isEmpty()) {
            DivineFavor.logger.error("Recipe error: " + result.item + ". No calling stones defined.")
            return emptyList()
        }

        val ingredientStacks = ArrayList<ItemStack>()
        for (ingredient in ingredients) {
            val itemStack = ingredient.toItemStack()
            if (itemStack.isEmpty) {
                DivineFavor.logger.error("Recipe error: " + result.item + ". Ingredient is invalid:" + ingredient.item)
                return ArrayList()
            }
            ingredientStacks.add(itemStack)
        }

        val recipeList = ArrayList<ImmaterialMediumRecipe>()
        for (callingStone in callingStones)
            recipeList.add(RecipeBuilder(resultStack, callingStone, ingredientStacks).create())
        return recipeList
    }

    private fun getCallingStone(stone: String): ItemCallingStone? {
        val stoneItem = Item.getByNameOrId(stone)
        if (stoneItem !is ItemCallingStone) {
            DivineFavor.logger.error("Recipe error: " + result.item + ". Item is not a calling stone:" + stone)
            return null
        }
        return stoneItem
    }
}
