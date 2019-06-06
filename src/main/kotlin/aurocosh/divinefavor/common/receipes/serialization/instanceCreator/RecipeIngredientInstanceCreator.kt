package aurocosh.divinefavor.common.receipes.serialization.instanceCreator

import aurocosh.divinefavor.common.receipes.serialization.ingredients.RecipeIngredientStack
import com.google.gson.InstanceCreator

import java.lang.reflect.Type

class RecipeIngredientStackInstanceCreator : InstanceCreator<RecipeIngredientStack> {
    override fun createInstance(type: Type): RecipeIngredientStack {
        return RecipeIngredientStack()
    }
}
