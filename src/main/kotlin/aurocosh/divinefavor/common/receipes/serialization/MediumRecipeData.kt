package aurocosh.divinefavor.common.receipes.serialization

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.calling_stones.ItemCallingStone
import aurocosh.divinefavor.common.receipes.ImmaterialMediumRecipe
import aurocosh.divinefavor.common.receipes.RecipeBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import java.util.*

class MediumRecipeData {
    var stones: List<String>? = null
    var result: RecipeResult? = null
    var ingredients: List<RecipeIngredient>? = null

    fun toRecipes(): List<ImmaterialMediumRecipe> {
        val resultStack = result!!.toItemStack()
        if (resultStack.isEmpty) {
            DivineFavor.logger.error("Recipe error: " + result!!.item + ". Result is invalid:" + result!!.getItem())
            return ArrayList()
        }

        val callingStones = ArrayList<ItemCallingStone>()
        for (stoneName in stones!!) {
            val stoneItem = getCallingStone(stoneName)
            if (stoneItem != null)
                callingStones.add(stoneItem)
        }

        if (callingStones.isEmpty()) {
            DivineFavor.logger.error("Recipe error: " + result!!.getItem() + ". No calling stones defined.")
            return ArrayList()
        }

        val ingredientStacks = ArrayList<ItemStack>()
        for (ingredient in ingredients!!) {
            val itemStack = ingredient.toItemStack()
            if (itemStack.isEmpty) {
                DivineFavor.logger.error("Recipe error: " + result!!.getItem() + ". Ingredient is invalid:" + ingredient.item)
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
            DivineFavor.logger.error("Recipe error: " + result!!.getItem() + ". Item is not a calling stone:" + stone)
            return null
        }
        return stoneItem
    }
}
