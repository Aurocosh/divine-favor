package aurocosh.divinefavor.common.integrations.jei;

import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.receipes.ImmaterialMediumRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

public class ImmaterialMediumCategory implements IRecipeCategory<ImmaterialMediumWrapper> {

    private final IDrawable background;

    public ImmaterialMediumCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(new ResourceLocation(ConstResources.GUI_JEI_IMMATERIAL_MEDIUM_RECIPE), 0, 0, 146, 86);
    }

    @Override
    public String getUid() {
        return DivineFavorJEIPlugin.IMMATERIAL_MEDIUM_UID;
    }

    @Override
    public String getTitle() {
        return I18n.format("container." + DivineFavorJEIPlugin.IMMATERIAL_MEDIUM_UID + ".name");
    }

    @Override
    public String getModName() {
        return ConstMisc.MOD_NAME;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ImmaterialMediumWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup group = recipeLayout.getItemStacks();
        ImmaterialMediumRecipe recipe = recipeWrapper.recipe;

        group.init(0, true, 64, 34);
        group.set(0, Arrays.asList(Ingredient.fromItem(ModItems.INSTANCE.getRitual_pouch()).getMatchingStacks()));

        group.init(1, true, 91, 34);
        group.set(1, Arrays.asList(recipe.callingStone.getMatchingStacks()));

        group.init(2, true, 124, 34);
        group.set(2, recipe.result);

        int[][] positions = new int[][]{
                {7, 17}, //Top left
                {25, 17}, //Top center
                {43, 17}, //Top right
                {25, 35}, //Center
                {7, 53}, //Bottom left
                {25, 53}, //Bottom center
                {43, 53}, //Bottom right
        };
        for (int i = 0; i < recipe.ingredients.length; i++) {
            group.init(i + 3, true, positions[i][0] - 1, positions[i][1] - 1);
            group.set(i + 3, Arrays.asList(recipe.ingredients[i].getMatchingStacks()));
        }
    }
}