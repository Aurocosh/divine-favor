package aurocosh.divinefavor.common.integrations.crafttweaker;

import aurocosh.divinefavor.common.receipes.RecipeLoader;
import crafttweaker.IAction;

public class ActionRemoveMediumRecipe implements IAction {
    private final String path;

    public ActionRemoveMediumRecipe(String path) {
        this.path = path;
    }

    @Override
    public void apply () {
        RecipeLoader.removeRecipe(path);
    }

    @Override
    public String describe () {
        return String.format("Removed medium recipe: %s", path);
    }
}
