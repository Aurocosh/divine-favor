package aurocosh.divinefavor.common.receipes;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.IntStream;

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