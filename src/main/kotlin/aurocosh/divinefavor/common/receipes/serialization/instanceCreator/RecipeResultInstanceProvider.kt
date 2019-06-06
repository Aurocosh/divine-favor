package aurocosh.divinefavor.common.receipes.serialization.instanceCreator

import aurocosh.divinefavor.common.receipes.serialization.RecipeResult
import com.google.gson.InstanceCreator

import java.lang.reflect.Type

class RecipeResultInstanceProvider : InstanceCreator<RecipeResult> {
    override fun createInstance(type: Type): RecipeResult {
        return RecipeResult()
    }
}
