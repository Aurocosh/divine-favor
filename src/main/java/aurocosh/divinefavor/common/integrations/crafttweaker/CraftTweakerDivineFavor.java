package aurocosh.divinefavor.common.integrations.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.DivineFavor")
public class CraftTweakerDivineFavor {
    @ZenMethod
    public static void addMediumRecipe(IItemStack output, IIngredient[] callingStones, IIngredient[] inputs) {
        CraftTweakerAPI.apply(new ActionAddMediumRecipe(output, callingStones, inputs));
    }

    @ZenMethod
    public static void removeMediumRecipe(String path) {
        CraftTweakerAPI.apply(new ActionRemoveMediumRecipe(path));
    }
}