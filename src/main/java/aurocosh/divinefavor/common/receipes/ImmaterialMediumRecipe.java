package aurocosh.divinefavor.common.receipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class ImmaterialMediumRecipe {
    public final ItemStack result;
    public final Ingredient callingStone;
    public final Ingredient[] ingredients;

    public ImmaterialMediumRecipe(ItemStack result, Ingredient callingStone, Ingredient[] ingredients) {
        this.result = result;
        this.callingStone = callingStone;
        this.ingredients = ingredients;
    }

    public ResourceLocation getName() {
        return result.getItem().getRegistryName();
    }
}