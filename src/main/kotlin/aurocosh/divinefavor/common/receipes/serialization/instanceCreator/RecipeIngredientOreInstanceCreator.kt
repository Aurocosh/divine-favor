package aurocosh.divinefavor.common.receipes.serialization.instanceCreator

import aurocosh.divinefavor.common.receipes.serialization.ingredients.RecipeIngredientOre
import com.google.gson.InstanceCreator

import java.lang.reflect.Type

class RecipeIngredientOreInstanceCreator : InstanceCreator<RecipeIngredientOre> {
    override fun createInstance(type: Type): RecipeIngredientOre {
        return RecipeIngredientOre()
    }
}
