package aurocosh.divinefavor.common.receipes;

import aurocosh.divinefavor.common.item.calling_stones.CallingStone;
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones;
import aurocosh.divinefavor.common.item.common.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class RecipeBuilder {
    public ResourceLocation name;
    public ItemStack result;
    private Ingredient callingStone;
    private List<Ingredient> ingredients;

    public RecipeBuilder(ItemStack result, CallingStone callingStone) {
        name = result.getItem().getRegistryName();
        this.result = result;
        this.callingStone = Ingredient.fromStacks(ModItems.getCallingStone(callingStone));
        ingredients = new ArrayList<>();
    }

    public RecipeBuilder addIngredient(Item item, int count){
        ingredients.add(Ingredient.fromStacks(new ItemStack(item,count)));
        return this;
    }

    public RecipeBuilder addIngredient(Item item){
        ingredients.add(Ingredient.fromItems(item));
        return this;
    }

    public ImmaterialMediumRecipe create(){
        Ingredient[] ingredientsArray = new Ingredient[ingredients.size()];
        ingredientsArray = ingredients.toArray(ingredientsArray);

        return new ImmaterialMediumRecipe(name,result,callingStone,ingredientsArray);
    }
}
