package aurocosh.divinefavor.common.receipes.serialization

import com.google.gson.InstanceCreator

import java.lang.reflect.Type

class RecipeIngredientInstanceCreator : InstanceCreator<RecipeIngredient> {
    override fun createInstance(type: Type): RecipeIngredient {
        return RecipeIngredient()
    }
}
