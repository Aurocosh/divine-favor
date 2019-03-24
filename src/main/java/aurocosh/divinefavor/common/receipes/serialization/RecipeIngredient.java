package aurocosh.divinefavor.common.receipes.serialization;

import aurocosh.divinefavor.DivineFavor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipeIngredient {
    public String item;
    public int data;
    public int count;

    public RecipeIngredient() {
        this.item = "minecraft:stick";
        this.data = -1;
        this.count = 1;
    }

    public Item getItem() {
        Item stackItem = Item.getByNameOrId(item);
        if (stackItem == null) {
            DivineFavor.logger.error("Recipe ingredient not found:" + item);
            return null;
        }
        return stackItem;
    }

    public ItemStack toItemStack() {
        ItemStack ingredientStack = new ItemStack(getItem(), count);
        if (data != -1)
            ingredientStack.setItemDamage(data);
        return ingredientStack;
    }
}
