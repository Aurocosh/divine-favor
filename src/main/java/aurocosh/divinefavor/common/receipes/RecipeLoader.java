package aurocosh.divinefavor.common.receipes;

import aurocosh.divinefavor.common.receipes.serialization.MediumRecipeData;
import aurocosh.divinefavor.common.receipes.serialization.RecipeIngredient;
import aurocosh.divinefavor.common.receipes.serialization.RecipeIngredientInstanceCreator;
import aurocosh.divinefavor.common.util.UtilAssets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.util.ArrayList;
import java.util.List;

public class RecipeLoader {
    public static void init() {
        List<ModContainer> mods = Loader.instance().getActiveModList();
        List<MediumRecipeData> recipes = new ArrayList<>();

        Gson gson = new GsonBuilder().registerTypeAdapter(RecipeIngredient.class, new RecipeIngredientInstanceCreator()).create();

        for (ModContainer container : mods) {
            List<String> recipePaths = UtilAssets.getAssetPaths(container, "medium_recipes", "json");
            List<MediumRecipeData> modRecipes = UtilAssets.loadObjectsFromJsonAssets(MediumRecipeData.class, container, recipePaths, gson);
            recipes.addAll(modRecipes);
        }

        for (MediumRecipeData recipe : recipes) {
            ImmaterialMediumRecipe mediumRecipe = recipe.toRecipe();
            if (mediumRecipe != null)
                ModRecipes.register(mediumRecipe);
        }
    }
}
