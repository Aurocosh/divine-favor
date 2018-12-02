package aurocosh.divinefavor.common.receipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class ImmaterialMediumRecipe {
    public final ResourceLocation name;
    public final Ingredient[] ingredients;
    public final ItemStack result;
    public final int time;

    public ImmaterialMediumRecipe(ResourceLocation name, ItemStack result, int time, Ingredient... ingredients) {
        this.name = name;
        this.ingredients = ingredients;
        this.result = result;
        this.time = time;
    }

    public ImmaterialMediumRecipe register() {
        ModRecipes.recipes.put(this.name, this);
        return this;
    }
}