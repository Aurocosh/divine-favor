package aurocosh.divinefavor.common.integrations.crafttweaker

import aurocosh.divinefavor.common.receipes.RecipeLoader
import crafttweaker.IAction

class ActionRemoveMediumRecipe(private val path: String) : IAction {
    override fun apply() {
        RecipeLoader.removeRecipe(path)
    }

    override fun describe(): String {
        return String.format("Removed medium recipe: %s", path)
    }
}
