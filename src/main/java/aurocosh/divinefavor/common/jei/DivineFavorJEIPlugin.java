package aurocosh.divinefavor.common.jei;

import aurocosh.divinefavor.common.block.common.ModBlocks;
import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.item.base.IDescriptionProvider;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.receipes.ImmaterialMediumRecipe;
import aurocosh.divinefavor.common.receipes.ModRecipes;
import aurocosh.divinefavor.common.registry.ModRegistries;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

@JEIPlugin
public class DivineFavorJEIPlugin implements IModPlugin {
    public static final String IMMATERIAL_MEDIUM_UID = ResourceNamer.getFullName("immaterial_medium").toString();

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper helper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new ImmaterialMediumCategory(helper));
    }

    @Override
    public void register(IModRegistry registry) {
        registerDescription(registry);
        registerRecipes(registry);
    }

    private void registerRecipes(IModRegistry registry) {
        registry.handleRecipes(ImmaterialMediumRecipe.class, ImmaterialMediumWrapper::new, IMMATERIAL_MEDIUM_UID);
        registry.addRecipes(ModRecipes.recipes.values(), IMMATERIAL_MEDIUM_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.medium_iron), IMMATERIAL_MEDIUM_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.medium_gold), IMMATERIAL_MEDIUM_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.medium_lapis), IMMATERIAL_MEDIUM_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.medium_log), IMMATERIAL_MEDIUM_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.medium_obsidian), IMMATERIAL_MEDIUM_UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.medium_redstone), IMMATERIAL_MEDIUM_UID);
    }

    private void registerDescription(IModRegistry registry) {
        for (final ModItem item : ModRegistries.items.getValues()) {
            String descriptionKey = item.getDescriptionKey();
//            registry.addIngredientInfo(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE), ItemStack.class, descriptionKey);
            registry.addIngredientInfo(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE), VanillaTypes.ITEM, descriptionKey);
        }
    }
}