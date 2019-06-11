package aurocosh.divinefavor.common.integrations.crafttweaker

import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.name
import aurocosh.divinefavor.common.receipes.ImmaterialMediumRecipe
import aurocosh.divinefavor.common.receipes.common.ModMediumRecipes
import com.blamejared.mtlib.helpers.InputHelper
import crafttweaker.IAction
import crafttweaker.api.item.IIngredient
import crafttweaker.api.item.IItemStack
import crafttweaker.api.minecraft.CraftTweakerMC

class ActionAddMediumRecipe(output: IItemStack, callingStones: Array<IIngredient>, inputs: Array<IIngredient>) : IAction {
    private val recipes: List<ImmaterialMediumRecipe>

    init {
        val outputStack = InputHelper.toStack(output)
        val stones = callingStones.S.map(CraftTweakerMC::getIngredient)
        val ingredients = inputs.S.map(CraftTweakerMC::getIngredient).toList()
        recipes = stones.map { ImmaterialMediumRecipe(outputStack, it, ingredients) }.toList()
    }

    override fun apply() {
        for (recipe in recipes)
            ModMediumRecipes.register(recipe)
    }

    override fun describe(): String {
        if (recipes.isEmpty())
            return ""
        val name = recipes[0].result.item.name
        return String.format("Added medium recipe for %s", name)
    }
}
