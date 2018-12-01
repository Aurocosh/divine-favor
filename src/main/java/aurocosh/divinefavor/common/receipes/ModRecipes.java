package aurocosh.divinefavor.common.receipes;

import aurocosh.divinefavor.common.constants.LibItemNames;
import aurocosh.divinefavor.common.constants.LibMisc;
import aurocosh.divinefavor.common.item.base.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ModRecipes {
    public static final Map<ResourceLocation,ImmaterialMediumRecipe> recipes = new HashMap<>();

    public static void init() {
    new ImmaterialMediumRecipe(new ResourceLocation(LibMisc.MOD_ID, LibItemNames.ARROW_THROW_TALISMAN),
            new ItemStack(ModItems.arrowThrowTalisman),
            10,
            Ingredient.fromStacks(new ItemStack(Items.ARROW,8)),
            Ingredient.fromItem(Items.GOLD_INGOT)
    ).register();
    }
}
