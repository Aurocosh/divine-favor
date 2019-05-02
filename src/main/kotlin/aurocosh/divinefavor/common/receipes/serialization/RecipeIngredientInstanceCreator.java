package aurocosh.divinefavor.common.receipes.serialization;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

public class RecipeIngredientInstanceCreator implements InstanceCreator<RecipeIngredient> {
    public RecipeIngredient createInstance(Type type) {
        return new RecipeIngredient();
    }
}
