package aurocosh.divinefavor.common.integrations.crafttweaker

import aurocosh.divinefavor.common.lib.extensions.name
import aurocosh.divinefavor.common.receipes.ImmaterialMediumRecipe
import aurocosh.divinefavor.common.receipes.common.ModMediumRecipes
import com.blamejared.mtlib.helpers.InputHelper
import crafttweaker.IAction
import crafttweaker.api.item.IIngredient
import crafttweaker.api.item.IItemStack
import crafttweaker.api.minecraft.CraftTweakerMC

class ActionAddMediumRecipe(output: IItemStack, callingStones: Array<IIngredient>, inputs: Array<IIngredient>) : IAction {
    private val recipe: ImmaterialMediumRecipe

    init {
        val outputStack = InputHelper.toStack(output)
        val stones = callingStones.map(CraftTweakerMC::getIngredient)
        val ingredients = inputs.map(CraftTweakerMC::getIngredient)
        recipe = ImmaterialMediumRecipe(outputStack, stones, ingredients)
    }

    override fun apply() {
        ModMediumRecipes.register(recipe)
    }

    override fun describe(): String {
        return String.format("Added medium recipe for %s", recipe.result.item.name)
    }
}
