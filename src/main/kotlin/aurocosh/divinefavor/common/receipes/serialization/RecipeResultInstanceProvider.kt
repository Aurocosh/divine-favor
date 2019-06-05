package aurocosh.divinefavor.common.receipes.serialization

import com.google.gson.InstanceCreator

import java.lang.reflect.Type

class RecipeResultInstanceProvider : InstanceCreator<RecipeResult> {
    override fun createInstance(type: Type): RecipeResult {
        return RecipeResult()
    }
}
