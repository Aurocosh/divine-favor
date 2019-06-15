package aurocosh.divinefavor.common.integrations.jei

import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.receipes.ContactRitualRecipe
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.ingredients.VanillaTypes
import mezz.jei.api.recipe.IRecipeWrapper


class ContactRitualWrapper(val recipe: ContactRitualRecipe) : IRecipeWrapper {
    override fun getIngredients(ingredients: IIngredients) {
        val inputs = recipe.blend.getMatchingStacks().S + recipe.markedGlass.getMatchingStacks().S
        ingredients.setInputs(VanillaTypes.ITEM, inputs.toList())
        ingredients.setOutput(VanillaTypes.ITEM, recipe.result)
    }
}