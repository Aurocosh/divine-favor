package aurocosh.divinefavor.common.receipes;

import aurocosh.divinefavor.common.receipes.serialization.MediumRecipeData;
import aurocosh.divinefavor.common.receipes.serialization.RecipeIngredient;
import aurocosh.divinefavor.common.receipes.serialization.RecipeIngredientInstanceCreator;
import aurocosh.divinefavor.common.util.UtilAssets;
import aurocosh.divinefavor.common.util.UtilList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecipeLoader {
    private final static Set<String> ignoredRecipes = new HashSet<>();

    public static void removeRecipe(String location) {
        ignoredRecipes.add(location);
    }

    public static void init() {
        List<ModContainer> mods = Loader.instance().getActiveModList();

        Gson gson = new GsonBuilder().registerTypeAdapter(RecipeIngredient.class, new RecipeIngredientInstanceCreator()).create();

        List<MediumRecipeData> recipes = new ArrayList<>();
        for (ModContainer container : mods) {
            List<String> recipePaths = UtilAssets.getAssetPaths(container, "medium_recipes", "json");
            Set<String> recipeSet = new HashSet<>(recipePaths);
            recipeSet.removeAll(ignoredRecipes);
            recipePaths = new ArrayList<>(recipeSet);

            List<MediumRecipeData> modRecipes = UtilAssets.loadObjectsFromJsonAssets(MediumRecipeData.class, container, recipePaths, gson);
            recipes.addAll(modRecipes);
        }

        UtilList.aggregate(recipes, MediumRecipeData::toRecipes).forEach(ModRecipes::register);
    }
}
