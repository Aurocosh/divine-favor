package aurocosh.divinefavor.common.receipes

import aurocosh.divinefavor.common.lib.RuntimeTypeAdapterFactory
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.receipes.common.ModMediumRecipes
import aurocosh.divinefavor.common.receipes.serialization.*
import aurocosh.divinefavor.common.receipes.serialization.ingredients.RecipeIngredient
import aurocosh.divinefavor.common.receipes.serialization.ingredients.RecipeIngredientOre
import aurocosh.divinefavor.common.receipes.serialization.ingredients.RecipeIngredientStack
import aurocosh.divinefavor.common.receipes.serialization.instanceCreator.RecipeIngredientOreInstanceCreator
import aurocosh.divinefavor.common.receipes.serialization.instanceCreator.RecipeIngredientStackInstanceCreator
import aurocosh.divinefavor.common.receipes.serialization.instanceCreator.RecipeResultInstanceProvider
import aurocosh.divinefavor.common.util.UtilAssets
import com.google.gson.GsonBuilder
import net.minecraftforge.fml.common.Loader
import java.util.*

object RecipeLoader {
    private val removedRecipes = HashSet<String>()

    fun removeRecipe(result: String) {
        removedRecipes.add(result)
    }

    fun init() {
        val mods = Loader.instance().activeModList

        val factory = RuntimeTypeAdapterFactory.of(RecipeIngredient::class.java)
                .registerSubtype(RecipeIngredientOre::class.java, "ore")
                .registerSubtype(RecipeIngredientStack::class.java, "stack");

        val gson = GsonBuilder()
                .registerTypeAdapter(RecipeIngredientOre::class.java, RecipeIngredientOreInstanceCreator())
                .registerTypeAdapter(RecipeIngredientStack::class.java, RecipeIngredientStackInstanceCreator())
                .registerTypeAdapter(RecipeResult::class.java, RecipeResultInstanceProvider())
                .registerTypeAdapterFactory(factory)
                .create()

        val recipes = ArrayList<MediumRecipeData>()
        for (container in mods) {
            val recipePaths: List<String> = UtilAssets.getAssetPaths(container, "medium_recipes", "json")
            val allRecipes = UtilAssets.loadObjectsFromJsonAssets(MediumRecipeData::class.java, container, recipePaths, gson)
            val availableRecipes = allRecipes.S.filter { !removedRecipes.contains(it.result.item) }
            recipes.addAll(availableRecipes)
        }
        recipes.S.map(MediumRecipeData::toRecipes).filterNotNull().forEach(ModMediumRecipes::register)
    }
}
