package aurocosh.divinefavor.common.receipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class ImmaterialMediumRecipe {
    public final ResourceLocation name;
    public final ItemStack result;
    public final Ingredient callingStone;
    public final Ingredient[] ingredients;

    public ImmaterialMediumRecipe(ResourceLocation name, ItemStack result, Ingredient callingStone, Ingredient[] ingredients) {
        this.name = name;
        this.result = result;
        this.callingStone = callingStone;
        this.ingredients = ingredients;
    }

    public ImmaterialMediumRecipe register() {
        ModRecipes.recipes.put(this.name, this);
        return this;
    }
}