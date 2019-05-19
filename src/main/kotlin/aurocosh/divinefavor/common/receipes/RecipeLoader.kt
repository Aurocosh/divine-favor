package aurocosh.divinefavor.common.receipes

import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.receipes.serialization.MediumRecipeData
import aurocosh.divinefavor.common.receipes.serialization.RecipeIngredient
import aurocosh.divinefavor.common.receipes.serialization.RecipeIngredientInstanceCreator
import aurocosh.divinefavor.common.util.UtilAssets
import com.google.gson.GsonBuilder
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.Loader
import java.util.*

object RecipeLoader {
    private val removedRecipes = HashSet<String>()

    fun removeRecipe(result: String) {
        removedRecipes.add(result)
    }

    fun init() {
        val mods = Loader.instance().activeModList
        val gson = GsonBuilder().registerTypeAdapter(RecipeIngredient::class.java, RecipeIngredientInstanceCreator()).create()

        val recipes = ArrayList<MediumRecipeData>()
        for (container in mods) {
            val recipePaths: List<String> = UtilAssets.getAssetPaths(container, "medium_recipes", "json")
            val allRecipes = UtilAssets.loadObjectsFromJsonAssets(MediumRecipeData::class.java, container, recipePaths, gson)
            val availableRecipes = allRecipes.S.filter { !removedRecipes.contains(it.result?.item) }
            recipes.addAll(availableRecipes)
        }
        recipes.S.map(MediumRecipeData::toRecipes).flatten().forEach(ModRecipes::register)
    }
}
