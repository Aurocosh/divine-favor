package aurocosh.divinefavor.common.receipes

import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.receipes.serialization.MediumRecipeData
import aurocosh.divinefavor.common.receipes.serialization.RecipeIngredient
import aurocosh.divinefavor.common.receipes.serialization.RecipeIngredientInstanceCreator
import aurocosh.divinefavor.common.util.UtilAssets
import com.google.gson.GsonBuilder
import net.minecraftforge.fml.common.Loader
import java.util.*

object RecipeLoader {
    private val ignoredRecipes = HashSet<String>()

    fun removeRecipe(location: String) {
        ignoredRecipes.add(location)
    }

    fun init() {
        val mods = Loader.instance().activeModList
        val gson = GsonBuilder().registerTypeAdapter(RecipeIngredient::class.java, RecipeIngredientInstanceCreator()).create()

        val recipes = ArrayList<MediumRecipeData>()
        for (container in mods) {
            var recipePaths: List<String> = UtilAssets.getAssetPaths(container, "medium_recipes", "json")
            val recipeSet = HashSet(recipePaths)
            recipeSet.removeAll(ignoredRecipes)
            recipePaths = ArrayList(recipeSet)

            val modRecipes = UtilAssets.loadObjectsFromJsonAssets(MediumRecipeData::class.java, container, recipePaths, gson)
            recipes.addAll(modRecipes)
        }
        recipes.S.map(MediumRecipeData::toRecipes).flatten().forEach(ModRecipes::register)
    }
}
