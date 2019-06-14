package aurocosh.divinefavor.common.receipes.serialization

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.gems.ItemCallingStone
import aurocosh.divinefavor.common.receipes.ImmaterialMediumRecipe
import aurocosh.divinefavor.common.receipes.serialization.ingredients.RecipeIngredient
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import java.util.*

class MediumRecipeData {
    var result: RecipeResult = RecipeResult()
    var stones: List<String> = emptyList()
    var ingredients: List<RecipeIngredient> = emptyList()

    fun toRecipes(): ImmaterialMediumRecipe? {
        DivineFavor.logger.info("Processing recipe for: ${result.item}.")

        val resultStack = result.toItemStack()
        val stoneIngredients = stones.mapNotNull(this::getCallingStone).map(Ingredient::fromItem)
        val ingredients = ingredients.mapNotNull(RecipeIngredient::toIngredient)

        if (!validate(resultStack, stoneIngredients, ingredients))
            return null

        return ImmaterialMediumRecipe(resultStack, stoneIngredients, ingredients)
    }

    private fun getCallingStone(stone: String): ItemCallingStone? {
        val stoneItem = Item.getByNameOrId(stone)
        if (stoneItem !is ItemCallingStone) {
            DivineFavor.logger.error("Recipe error: ${result.item}. Item is not a calling stone:" + stone)
            return null
        }
        return stoneItem
    }

    private fun validate(result: ItemStack, stones: List<Ingredient>, ingredients: List<Ingredient>): Boolean {
        if (result.isEmpty) {
            DivineFavor.logger.error("Recipe error: ${result.item}. Result is invalid: ${result.item}")
            return false
        }
        if (stones.isEmpty()) {
            DivineFavor.logger.error("Recipe error: ${result.item}. No calling stones defined.")
            return false
        }
        if (ingredients.isEmpty()) {
            DivineFavor.logger.error("Recipe error: ${result.item}. No ingredients defined.")
            return false
        }
        return true
    }
}
